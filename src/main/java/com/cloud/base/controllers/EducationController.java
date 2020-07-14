package com.cloud.base.controllers;

import com.cloud.base.dto.EducationDTO;
import com.cloud.base.response.Response;
import com.cloud.base.service.EducationService;
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
@RequestMapping("/api/education")
@RestController
public class EducationController {

    @Autowired
    EducationService educationService;

    @GetMapping("")
    public Response getEducations(Pageable pageable) {
        return new Response(educationService.getAllEducations(pageable));
    }

    @PostMapping("")
    public Response createEducation(@Valid @RequestBody EducationDTO educationDTO) {
        return new Response(educationService.save(educationDTO));
    }

    @PutMapping("")
    public Response updateEducation(@Valid @RequestBody EducationDTO educationDTO) {
        return new Response(educationService.update(educationDTO));
    }

    @DeleteMapping("/{id}")
    public Response deleteEducation(@PathVariable Long id) {
        educationService.delete(id);
        return new Response("deleted");
    }
}
