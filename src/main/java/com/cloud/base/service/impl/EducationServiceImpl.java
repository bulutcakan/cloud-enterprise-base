package com.cloud.base.service.impl;

import com.cloud.base.dto.EducationDTO;
import com.cloud.base.exception.EducationException;
import com.cloud.base.models.Education;
import com.cloud.base.repository.EducationRepository;
import com.cloud.base.service.EducationService;
import com.cloud.base.util.CloudMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    EducationRepository educationRepository;

    @Autowired
    CloudMapper cloudMapper;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public EducationDTO save(EducationDTO educationDTO) {
        if (null != educationDTO.getId())
            throw new EducationException("Education id must be null");

        Education education = modelMapper.map(educationDTO, Education.class);
        educationRepository.save(education);
        return modelMapper.map(education, EducationDTO.class);
    }

    @Override
    public List<EducationDTO> getAllEducations(Pageable pageable) {
        List<Education> educationList = educationRepository.findAll();
        return cloudMapper.convertToList(educationList, EducationDTO.class);
    }

    @Override
    public EducationDTO update(EducationDTO educationDTO) {
        Education education = getById(educationDTO.getId());
        education.setName(educationDTO.getName());
        return modelMapper.map(education, EducationDTO.class);
    }

    @Override
    public void delete(Long id) {
        Education education = getById(id);
        educationRepository.delete(education);
    }

    @Override
    public EducationDTO getDTOById(Long id) {
        Education education = getById(id);
        return modelMapper.map(education, EducationDTO.class);
    }

    @Override
    public Education getById(Long id) {
        return educationRepository.findById(id).orElseThrow(() -> new EducationException("Education type not found with id " + id));
    }
}
