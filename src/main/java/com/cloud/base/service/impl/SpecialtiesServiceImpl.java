package com.cloud.base.service.impl;

import com.cloud.base.dto.SpecialtiesDTO;
import com.cloud.base.service.SpecialtiesService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SpecialtiesServiceImpl implements SpecialtiesService {

    @Override
    public SpecialtiesDTO save(SpecialtiesDTO specialitesDTO) {
        return null;
    }

    @Override
    public List<SpecialtiesDTO> getAllSpecialities(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public SpecialtiesDTO update(SpecialtiesDTO specialitesDTO) {
        return null;
    }

    @Override
    public SpecialtiesDTO getById(Long id) {
        return null;
    }
}
