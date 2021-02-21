package com.willow.service.spring.boot.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "willow.service")
@Data
@ToString
public class ServiceProperties {
    private String id = "1001";
    private String name = "willow";
}
