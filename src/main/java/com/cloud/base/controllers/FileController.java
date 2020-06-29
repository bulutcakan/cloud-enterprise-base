package com.cloud.base.controllers;

import com.cloud.base.response.Response;
import com.cloud.base.service.AzureFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AzureFileService azureFileService;

    @PostMapping("/container")
    public Response createContainer(@RequestBody String containerName) {
        boolean created = azureFileService.createContainer(containerName);
        return new Response("OK");
    }

    @PostMapping
    public Response upload(@RequestParam(value = "file") MultipartFile multipartFile) {
        URI url = azureFileService.upload(multipartFile);
        return new Response(url);
    }

    @GetMapping("/blobs")
    public Response getAllBlobs(@RequestParam String containerName) {
        List<URI> uris = azureFileService.listBlobs(containerName);
        return new Response(uris);
    }

    @DeleteMapping
    public Response delete(@RequestParam String blobName) {
        azureFileService.deleteBlob(blobName);
        return new Response("OK");
    }


}
