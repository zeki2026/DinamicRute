package com.chapter.bd.tools.app.chapterbd_tools;

import javax.sql.DataSource;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class OracleConfig {

    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("oracle.jdbc.OracleDriver");
        
        // Connection pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(60000);
        
        // Oracle-specific settings
        config.addDataSourceProperty("oracle.net.CONNECT_TIMEOUT", 10000);
        config.addDataSourceProperty("oracle.jdbc.ReadTimeout", 30000);
        config.addDataSourceProperty("oracle.jdbc.timezoneAsRegion", "false");
        config.addDataSourceProperty("oracle.jdbc.fanEnabled", "false");
        
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        
        // Escanea solo el paquete del repositorio ya que no usas entidades
        em.setPackagesToScan("com.chapter.bd.tools.app.chapterbd_tools.repository");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);
        
        // Configuración explícita para Oracle (importante)
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");
        em.setJpaVendorAdapter(vendorAdapter);
        
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none"); // Cambiado de "validate" a "none"
        jpaProperties.put("hibernate.boot.allow_jdbc_metadata_access", "true"); // Cambiado a true
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        jpaProperties.put("hibernate.jdbc.time_zone", "UTC");
        em.setJpaProperties(jpaProperties);
        
        return em;
    }
}