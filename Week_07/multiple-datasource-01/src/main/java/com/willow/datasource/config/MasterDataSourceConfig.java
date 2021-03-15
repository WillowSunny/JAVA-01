package com.willow.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MasterDataSourceConfig {

    @Primary
    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource getMasterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("masterJdbcTemplate")
    JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource")DataSource masterDataSource){
        return new JdbcTemplate(masterDataSource);
    }

}
