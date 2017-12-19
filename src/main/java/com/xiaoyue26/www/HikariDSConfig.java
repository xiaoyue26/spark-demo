package com.xiaoyue26.www;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by xiaoyue26 on 17/12/19.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariDSConfig extends HikariConfig {
    @Bean
    public DataSource dataSource() throws SQLException {
        return new HikariDataSource(this);
    }
}

