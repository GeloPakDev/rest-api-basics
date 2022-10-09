package com.epam.esm;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db_mysql.properties")
public class AppConfig {
    @Autowired
    Environment environment;

    private final String URL = "url";
    private final String USER = "user";
    private final String DRIVER = "driver";
    private final String PASSWORD = "password";
    private final int initialSize = 10;

    @Bean
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty(DRIVER));
        basicDataSource.setUrl(environment.getProperty(URL));
        basicDataSource.setUsername(environment.getProperty(USER));
        basicDataSource.setPassword(environment.getProperty(PASSWORD));
        basicDataSource.setInitialSize(initialSize);
        return basicDataSource;
    }
}