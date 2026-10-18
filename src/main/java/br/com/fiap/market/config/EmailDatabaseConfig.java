package br.com.fiap.market.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class EmailDatabaseConfig {

    @Bean(defaultCandidate = false)
    @ConfigurationProperties("app.email-datasource")
    public DataSourceProperties emailDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(defaultCandidate = false)
    public HikariDataSource emailDataSource(
            @Qualifier("emailDataSourceProperties")
            DataSourceProperties properties
    ) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(defaultCandidate = false)
    public JdbcTemplate emailJdbcTemplate(
            @Qualifier("emailDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }
}