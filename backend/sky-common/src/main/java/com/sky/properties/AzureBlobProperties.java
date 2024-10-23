package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.azure.storage")
@Data
public class AzureBlobProperties {
    private String connectionString;
    private String containerName;
}
