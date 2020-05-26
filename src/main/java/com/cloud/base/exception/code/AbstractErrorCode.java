package com.cloud.base.exception.code;

import com.cloud.base.exception.base.CloudServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractErrorCode implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractErrorCode.class);

    private static final String SECTION_UNKNOWN = "UNKNOWN";

    private static final int MAX_SECTION_SIZE = 1000;

    // -----
    private static final ConcurrentMap<Integer, ErrorCode> errorCodes = new ConcurrentHashMap<>();
    private static final AtomicBoolean staticInitializationCompleted = new AtomicBoolean(false);

    // -----
    private int code;
    private String name;
    private SectionDef section;
    private String description;
    public AbstractErrorCode(int code) {
        this(code, null);
    }
    public AbstractErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
        validateAndAddErrorCode((ErrorCode) this);
    }

    // -----

    public static List<ErrorCode> getAll() {
        return new ArrayList<>(errorCodes.values());
    }

    protected static void baseInitialize() {
        if (!staticInitializationCompleted.compareAndSet(false, true)) {
            logger.warn("Already initialized");
            return;
        }

        List<SectionDef> sections = new ArrayList<>();

        // do all section interfaces have corresponding section annotations?
        for (Class<?> klass : ErrorCode.class.getClasses()) {
            if (klass.getDeclaringClass() != ErrorCode.class) {
                continue;
            }

            String sectionName = klass.getSimpleName();
            Section section = klass.getAnnotation(Section.class);

            if (section == null) {
                throw new CloudServiceException(String.format("Section annotation missing for [%s]", sectionName));
            }

            int sectionSize = section.max() - section.min();

            if (sectionSize > MAX_SECTION_SIZE) {
                throw new CloudServiceException(String.format("Section size is %d . It must be <= %d (ideally 1000) [%s]",
                        sectionSize, MAX_SECTION_SIZE, sectionName));
            }

            sections.add(new SectionDef(sectionName, section));
        }

        // are section enum constants overlapping?
        for (SectionDef section : sections) {
            for (SectionDef other : sections) {
                if (section.isUnknown() || other.isUnknown()) {
                    continue;
                }

                if (section == other) {
                    continue;
                }

                if (section.overlaps(other)) {
                    throw new CloudServiceException(String.format("Sections [%s] and [%s] overlap", section, other));
                }
            }
        }

        for (SectionDef section : sections) {
            initializeSection(section);
        }
    }

    // -----

    private static void initializeSection(SectionDef section) {
        if (section.isUnknown()) {
            return;
        }

        try {
            Class<?> klass =
                    Class.forName(ErrorCode.class.getCanonicalName() + "$" + section);

            if ((klass.getModifiers() & Modifier.PUBLIC) == 0) {
                throw new IllegalArgumentException(String.format("Section interface [%s] is not public", section));
            }

            for (Field field : klass.getFields()) {
                if (AbstractErrorCode.class.isAssignableFrom(field.getType())) {
                    AbstractErrorCode errorCode = (AbstractErrorCode) field.get(null);
                    errorCode.name = field.getName();
                    errorCode.section = section;

                    if (!section.contains(errorCode.code)) {
                        throw new IllegalArgumentException(
                                String.format("Error code for [%d] not in range [%d, %d) for section [%s]",
                                        errorCode.code, section.getRangeMin(), section.getRangeMax(), section.getName()));
                    }
                } else {
                    throw new IllegalArgumentException(
                            String.format("Section interface [%s] has non-ErrorCode field [%s] of type [%s]",
                                    section, field.getName(), field.getType().getCanonicalName()));
                }
            }
        } catch (ClassNotFoundException e) {
            logger.warn("Unused section [{}]", section);
        } catch (IllegalAccessException e) {
            throw new CloudServiceException(e.getMessage());
        }
    }

    // -----

    private static void validateAndAddErrorCode(ErrorCode errorCode) {
        ErrorCode prev = errorCodes.putIfAbsent(errorCode.getCode(), errorCode);

        if (prev != null) {
            throw new CloudServiceException("Duplicate error code [" + errorCode.getCode() + "]");
        }
    }

    public final int getCode() {
        return code;
    }

    public final String getName() {
        return name;
    }

    public final String getSection() {
        return section != null ? section.getName() : null;
    }

    // -----

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        return
                obj instanceof AbstractErrorCode &&
                        ((AbstractErrorCode) obj).code == this.code;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(code).hashCode();
    }

    // -----

    @Override
    public final String toString() {
        if (section != null && !section.isUnknown()) {
            return "ERROR[" + code + "/" + section.getName() + "." + getName() + "]";
        } else {
            return "ERROR[" + code + "]";
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public static @interface Section {
        int min();

        int max();
    }

    private static class SectionDef {
        private String name;
        private int rangeMin;
        private int rangeMax;

        public SectionDef(String name, Section section) {
            if (StringUtils.isEmpty(name)) {
                throw new IllegalArgumentException("name cannot be null or empty");
            }

            this.name = name;

            if (!isUnknown() && section == null) {
                throw new IllegalArgumentException("section annotation cannot be null for non-unknown sections");
            }

            if (section != null) {
                this.rangeMin = section.min();
                this.rangeMax = section.max();

                if (rangeMin < 0 || rangeMin >= rangeMax) {
                    throw new IllegalArgumentException(
                            String.format("Invalid range min: [%d], max: [%d]", rangeMin, rangeMax));
                }
            }
        }

        public String getName() {
            return name;
        }

        public int getRangeMin() {
            return rangeMin;
        }

        public int getRangeMax() {
            return rangeMax;
        }

        private boolean overlaps(SectionDef other) {
            if (other == null) {
                throw new IllegalArgumentException("Other section cannot be null");
            }

            // assumes both section ranges are valid
            return rangeMin < other.rangeMax && other.rangeMin < rangeMax;
        }

        private boolean contains(int code) {
            return isUnknown() || (code >= rangeMin && code < rangeMax);
        }

        private boolean isUnknown() {
            return SECTION_UNKNOWN.equals(name);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SectionDef other = (SectionDef) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, rangeMin, rangeMax);
        }
    }
}
