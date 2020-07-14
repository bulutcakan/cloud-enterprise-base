package com.cloud.base.service;

import com.cloud.base.dto.FileTypeDTO;
import com.cloud.base.models.FileType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FileTypeService {

    FileTypeDTO save(FileTypeDTO fileTypeDTO);

    List<FileTypeDTO> getAllFileTypes(Pageable pageable);

    void deleteFileType(Long typeId);

    FileTypeDTO update(FileTypeDTO fileTypeDTO);

    FileType getFileTypeById(Long id);
}
