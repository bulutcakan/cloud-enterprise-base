package com.cloud.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;

//IMPORTANT!!! DO NOT CHANGE THIS ORDER
@JsonPropertyOrder({"clz", "success", "data", "error"})
public class Response<T> {

    private final Logger log = LoggerFactory.getLogger(Response.class);

    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private boolean success = true;
    private Class clz;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseError error;

    public Response() {

    }

    public Response(Throwable cause) {
        this(cause, false);
    }

    public Response(Throwable cause, boolean withStackTrace) {
        setError(cause, withStackTrace);
    }

    public Response(T data) {
        this.clz = data.getClass();
        setData(data);
    }

    private void setError(Throwable exception, boolean withStackTrace) {
        markInvalid();

        this.error = new ResponseError(exception, withStackTrace);
    }

    private void markInvalid() {
        success = false;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        markInvalid();
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;

        if(data instanceof RestPageImpl || data instanceof PageImpl) {
            this.clz = RestPageImpl.class;
            this.data = convertPageTypeData(data);
            return;

        } else if(data instanceof LinkedHashMap) {
            this.data = convertMapTypeData(data);
            return;
        }

        if(null != this.clz) {
            this.data = (T) mapper.convertValue(this.data, this.clz);
        }
    }

    private T convertPageTypeData(T data) {
        RestPageImpl<T> page = data instanceof RestPageImpl ? ((RestPageImpl<T>) data) : new RestPageImpl<T>((PageImpl) data);
        List<T> content = page.getContent();

        if(!CollectionUtils.isEmpty(content)) {
            page.setClz(content.get(0).getClass());
            return (T) mapper.convertValue(page, RestPageImpl.class);
        }

        return data;
    }

    private T convertMapTypeData(T data) {
        try {
            if(null != this.clz && !this.clz.equals(RestPageImpl.class)) {
                return (T) mapper.convertValue(data, this.clz);
            }

            RestPageImpl<T> restPage = mapper.convertValue(data, RestPageImpl.class);

            if(null == restPage.getClz()) {
                return (T) restPage;
            }

            JsonNode node = mapper.convertValue(data, JsonNode.class);
            JavaType type = mapper.getTypeFactory().constructParametricType(RestPageImpl.class, restPage.getClz());
            return (T) mapper.convertValue(node, type);
        } catch (Exception e) {
            log.error("Got exception while setting response data", e);
            this.clz = JsonNode.class;
            return (T) mapper.convertValue(data, JsonNode.class);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        try {
            Class.forName( clz.getCanonicalName() );
            this.clz = clz;
        } catch( Exception e ) {
            this.clz = JsonNode.class;
        }
    }
}
