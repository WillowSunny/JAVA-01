package com.willow.service.spring.boot;

import com.willow.service.spring.boot.config.ServiceConfiguration;
import com.willow.service.spring.boot.properties.ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceProperties.class)
@ConditionalOnClass(ServiceConfiguration.class)
@ConditionalOnProperty(prefix = "willow.service", value = "enabled", matchIfMissing = true)
public class SpringBootConfiguration {
    @Autowired
    private ServiceProperties serviceProperties;

    @Bean
    @ConditionalOnMissingBean(ServiceConfiguration.class)
    public ServiceConfiguration serviceConfiguration(){
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        serviceConfiguration.setId(serviceProperties.getId());
        serviceConfiguration.setName(serviceProperties.getName());
        return serviceConfiguration;
    }
}
