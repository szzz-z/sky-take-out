package com.sky.context;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class AzureBlobClient {

    private static BlobServiceClient blobServiceClient;

    // 私有构造函数，防止外部实例化
    private AzureBlobClient() {}

    public static synchronized BlobServiceClient getBlobServiceClient(String connectionString) {
        if (blobServiceClient == null) {
            blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
        }
        return blobServiceClient;
    }
}
