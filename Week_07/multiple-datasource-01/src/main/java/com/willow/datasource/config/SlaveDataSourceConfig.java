package com.willow.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SlaveDataSourceConfig {

    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource getSlaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("slaveJdbcTemplate")
    JdbcTemplate masterJdbcTemplate(@Qualifier("slaveDataSource")DataSource slaveDataSource){
        return new JdbcTemplate(slaveDataSource);
    }
}
