package com.cloud.base.service;

import com.cloud.base.dto.EducationDTO;
import com.cloud.base.models.Education;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EducationService {

    EducationDTO save(EducationDTO educationDTO);

    List<EducationDTO> getAllEducations(Pageable pageable);

    EducationDTO update(EducationDTO educationDTO);

    void delete(Long id);

    EducationDTO getDTOById(Long id);

    Education getById(Long id);


}
