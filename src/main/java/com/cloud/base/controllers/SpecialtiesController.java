package com.cloud.base.controllers;

import com.cloud.base.dto.SpecialtiesDTO;
import com.cloud.base.response.Response;
import com.cloud.base.service.SpecialtiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/specialties")
@RestController
public class SpecialtiesController {

    @Autowired
    SpecialtiesService specialtiesService;

    @GetMapping("")
    public Response getAllSpec(Pageable pageable) {
        return new Response(specialtiesService.getAllSpecialities(pageable));
    }

    @PostMapping("")
    public Response createSpec(@Valid @RequestBody SpecialtiesDTO specialtiesDTO) {
        return new Response(specialtiesService.save(specialtiesDTO));
    }

    @PutMapping("")
    public Response updateFileType(@Valid @RequestBody SpecialtiesDTO specialtiesDTO) {
        return new Response(specialtiesService.update(specialtiesDTO));
    }

    @DeleteMapping("/{id}")
    public Response deleteSpec(@PathVariable Long id) {
        specialtiesService.delete(id);
        return new Response("deleted");
    }
}
