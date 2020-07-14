package com.cloud.base.service.impl;

import com.cloud.base.dto.SpecialtiesDTO;
import com.cloud.base.exception.SpecialtiesException;
import com.cloud.base.models.Specialties;
import com.cloud.base.repository.SpecialtiesRepository;
import com.cloud.base.service.SpecialtiesService;
import com.cloud.base.util.CloudMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SpecialtiesServiceImpl implements SpecialtiesService {

    @Autowired
    SpecialtiesRepository specialtiesRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CloudMapper cloudMapper;

    @Override
    public SpecialtiesDTO save(SpecialtiesDTO specialitesDTO) {
        Specialties specialties = modelMapper.map(specialitesDTO, Specialties.class);
        if (specialitesDTO.getId() == null) {
            specialtiesRepository.save(specialties);
            return modelMapper.map(specialties, SpecialtiesDTO.class);
        }
        throw new SpecialtiesException("Id must be null for " + specialitesDTO.getId());
    }

    @Override
    public List<SpecialtiesDTO> getAllSpecialities(Pageable pageable) {
        List<Specialties> specialties = specialtiesRepository.findAll(pageable).getContent();
        return cloudMapper.convertToList(specialties, SpecialtiesDTO.class);
    }

    @Override
    public void delete(Long id) {
        specialtiesRepository.delete(getById(id));
    }

    @Override
    public SpecialtiesDTO update(SpecialtiesDTO specialitesDTO) {
        Specialties specialties = getById(specialitesDTO.getId());
        specialties.setName(specialitesDTO.getName());
        specialties.setFileUrl(specialitesDTO.getFile().getURL());
        specialtiesRepository.save(specialties);
        return modelMapper.map(specialties, SpecialtiesDTO.class);
    }

    @Override
    public SpecialtiesDTO getDTOById(Long id) {
        return modelMapper.map(getById(id), SpecialtiesDTO.class);
    }

    @Override
    public Specialties getById(Long id) {
        return specialtiesRepository.findById(id).orElseThrow(() ->
                new SpecialtiesException("Specialty not found with id" + id));
    }


}
