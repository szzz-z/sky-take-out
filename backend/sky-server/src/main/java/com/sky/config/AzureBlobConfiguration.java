package com.sky.config;

import com.sky.properties.AzureBlobProperties;
import com.sky.utils.AzureBlobUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AzureBlobConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AzureBlobUtil azureBlobUtil(AzureBlobProperties azureBlobProperties) {
        log.info("azure云工具对象创建：{}", azureBlobProperties);
        return new AzureBlobUtil(azureBlobProperties.getConnectionString(), azureBlobProperties.getContainerName());
    }
}
