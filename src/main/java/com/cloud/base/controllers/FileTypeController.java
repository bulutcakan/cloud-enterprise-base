package com.cloud.base.controllers;

import com.cloud.base.dto.FileTypeDTO;
import com.cloud.base.response.Response;
import com.cloud.base.service.FileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/fileType")
@RestController
public class FileTypeController {

    @Autowired
    FileTypeService fileTypeService;

    @GetMapping("")
    public Response getAllFileTypes(Pageable pageable) {
        return new Response(fileTypeService.getAllFileTypes(pageable));
    }

    @PostMapping("")
    public Response createFileType(@Valid @RequestBody FileTypeDTO fileTypeDTO) {
        return new Response(fileTypeService.save(fileTypeDTO));
    }

    @PutMapping("")
    public Response updateFileType(@Valid @RequestBody FileTypeDTO fileTypeDTO) {
        return new Response(fileTypeService.update(fileTypeDTO));
    }

    @DeleteMapping("")
    public Response deleteFileType(Long fileType) {
        fileTypeService.deleteFileType(fileType);
        return new Response("deleted");
    }
}
