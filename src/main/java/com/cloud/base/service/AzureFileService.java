package com.cloud.base.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

public interface AzureFileService {

    boolean createContainer(String containerName);

    URI upload(MultipartFile multipartFile);

    List listBlobs(String containerName);

    void deleteBlob(String containerName, String blobName);

    void deleteBlob(String blobName);

}
