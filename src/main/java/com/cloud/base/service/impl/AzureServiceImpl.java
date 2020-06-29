package com.cloud.base.service.impl;

import com.cloud.base.service.AzureFileService;
import com.cloud.base.util.FileUtils;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AzureServiceImpl implements AzureFileService {

    private static final Logger logger = LoggerFactory.getLogger(AzureServiceImpl.class);

    @Autowired
    private CloudBlobClient cloudBlobClient;

    @Autowired
    private CloudBlobContainer cloudBlobContainer;

    @Override
    public boolean createContainer(String containerName) {
        boolean containerCreated = false;
        CloudBlobContainer container = null;
        try {
            container = cloudBlobClient.getContainerReference(containerName);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            containerCreated = container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return containerCreated;
    }

    @Override
    public URI upload(MultipartFile multipartFile) {

        URI uri = null;
        CloudBlockBlob blob = null;
        try {
            String fileUniqueName = FileUtils.getUniqueFileName(multipartFile.getOriginalFilename());
            blob = cloudBlobContainer.getBlockBlobReference(fileUniqueName);
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (Exception ex) {
            logger.error("Exception occurred while uploading file to Azure system");
            ex.printStackTrace();
        }
        return uri;
    }

    @Override
    public List listBlobs(String containerName) {
        List<URI> uris = new ArrayList<>();
        try {
            CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
            for (ListBlobItem blobItem : container.listBlobs()) {
                uris.add(blobItem.getUri());
            }
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (StorageException e) {
            logger.error(e.getMessage());
        }
        return uris;
    }

    @Override
    public void deleteBlob(String containerName, String blobName) {
        try {
            CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
            CloudBlockBlob blobToBeDeleted = container.getBlockBlobReference(blobName);
            blobToBeDeleted.deleteIfExists();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
