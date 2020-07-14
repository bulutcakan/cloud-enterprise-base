package com.cloud.base.service.impl;

import com.cloud.base.dto.FileTypeDTO;
import com.cloud.base.exception.FileTypeException;
import com.cloud.base.models.FileType;
import com.cloud.base.repository.FileTypeRepository;
import com.cloud.base.service.FileTypeService;
import com.cloud.base.util.CloudMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileTypeServiceImpl implements FileTypeService {

    @Autowired
    FileTypeRepository fileTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CloudMapper cloudMapper;

    @Override
    public FileTypeDTO save(FileTypeDTO fileTypeDTO) {
        if (fileTypeDTO.getId() == null) {
            FileType fileType = modelMapper.map(fileTypeDTO, FileType.class);
            fileTypeRepository.save(fileType);
            return modelMapper.map(fileType, FileTypeDTO.class);
        }

        throw new FileTypeException("File type id must be null on create operation !");
    }

    @Override
    public List<FileTypeDTO> getAllFileTypes(Pageable pageable) {
        List<FileType> fileTypes = fileTypeRepository.findAll();
        return cloudMapper.convertToList(fileTypes, FileTypeDTO.class);
    }


    @Override
    public void deleteFileType(Long typeId) {
        FileType fileType = getFileTypeById(typeId);
        fileTypeRepository.delete(fileType);
    }

    @Override
    public FileTypeDTO update(FileTypeDTO fileTypeDTO) {
        FileType fileType = getFileTypeById(fileTypeDTO.getId());
        fileType.setName(fileTypeDTO.getName());
        fileTypeRepository.save(fileType);
        return modelMapper.map(fileType, FileTypeDTO.class);
    }

    @Override
    public FileType getFileTypeById(Long id) {
        return fileTypeRepository.findById(id).orElseThrow(() -> new FileTypeException("File type not found with id " + id));
    }
}
