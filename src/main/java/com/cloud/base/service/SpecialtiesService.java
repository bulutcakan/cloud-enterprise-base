package com.cloud.base.service;

import com.cloud.base.dto.SpecialtiesDTO;
import com.cloud.base.models.Specialties;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpecialtiesService {

    SpecialtiesDTO save(SpecialtiesDTO specialitesDTO);

    List<SpecialtiesDTO> getAllSpecialities(Pageable pageable);

    void delete(Long id);

    SpecialtiesDTO update(SpecialtiesDTO specialitesDTO);

    SpecialtiesDTO getDTOById(Long id);

    Specialties getById(Long id);

}
