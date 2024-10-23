package com.sky.utils;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import com.sky.context.AzureBlobClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AzureBlobUtil {

    private String connectionString;
    private String containerName;

    public void deleteBlobByUrl(String blobUrl) {
        try {
            // 解析 URL 获取 Blob 名称
            String[] parts = blobUrl.split("/");
            String blobName = parts[parts.length - 1]; // 获取最后一部分作为 Blob 名称

            // 创建 BlobServiceClient 实例
            BlobServiceClient blobServiceClient = AzureBlobClient.getBlobServiceClient(connectionString);

            // 获取 BlobContainerClient
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            // 创建 BlobClient
            BlobClient blobClient = containerClient.getBlobClient(blobName);

            // 删除 Blob
            blobClient.delete();
            log.info("Blob {} 删除成功", blobName);
        } catch (Exception e) {
            log.error("删除 Blob 时出错: {}", e.getMessage());
        }
    }

    /**
     * 文件上传
     *
     * @param bytes
     * @param blobName
     * @return
     */
    public String upload(byte[] bytes, String blobName) {
        // 创建 BlobServiceClient 实例。
        BlobServiceClient blobServiceClient = AzureBlobClient.getBlobServiceClient(connectionString);

        // 获取 BlobContainerClient
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        // 如果容器不存在，则创建它
        if (!containerClient.exists()) {
            containerClient.create();
        }

        // 创建 BlobClient
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        // 上传文件
        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(bytes)) {
            blobClient.upload(dataStream, bytes.length, true);
        } catch (Exception e) {
            log.error("上传文件到 Azure Blob 存储时出错: {}", e.getMessage());
            return null;
        }

        // 获取账户名称
        String accountName = blobServiceClient.getAccountName();

        // 文件访问路径
        String fileUrl = String.format("https://%s.blob.core.windows.net/%s/%s",
                accountName,
                containerName,
                blobName);

        log.info("文件上传到: {}", fileUrl);
        return fileUrl;
    }
}

//package com.sky.utils;
//
//import com.azure.storage.blob.BlobServiceClient;
//import com.azure.storage.blob.BlobServiceClientBuilder;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobClient;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import java.io.ByteArrayInputStream;
//
//@Data
//@AllArgsConstructor
//@Slf4j
//public class AzureBlobUtil {
//
//    private String connectionString;
//    private String containerName;
//
//    /**
//     * 文件上传
//     *
//     * @param bytes
//     * @param blobName
//     * @return
//     */
//    public String upload(byte[] bytes, String blobName) {
//        // 创建BlobServiceClient实例。
//        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//                .connectionString(connectionString)
//                .buildClient();
//
//        // 获取容器客户端
//        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
//
//        // 如果容器不存在，则创建它
//        if (!containerClient.exists()) {
//            containerClient.create();
//        }
//
//        // 创建BlobClient
//        BlobClient blobClient = containerClient.getBlobClient(blobName);
//
//        // 上传文件
//        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(bytes)) {
//            blobClient.upload(dataStream, bytes.length, true);
//        } catch (Exception e) {
//            log.error("上传文件到 Azure Blob 存储时出错: {}", e.getMessage());
//            return null;
//        }
//
//        // 文件访问路径规则 https://<AccountName>.blob.core.windows.net/<ContainerName>/<BlobName>
//        String fileUrl = String.format("https://%s.blob.core.windows.net/%s/%s",
//                containerClient.getBlobServiceClient().getAccountName(),
//                containerName,
//                blobName);
//
//        log.info("文件上传到: {}", fileUrl);
//        return fileUrl;
//    }
//}
