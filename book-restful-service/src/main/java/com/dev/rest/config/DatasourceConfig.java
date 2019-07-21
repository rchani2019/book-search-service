package com.dev.rest.config;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatasourceConfig {
    
//	@Autowired
//    EntityManagerFactoryBuilder builder;
//	
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//	
	
	/*
    @Autowired
    EntityManagerFactoryBuilder builder;

    @Bean
    @ConfigurationProperties(prefix = "datasource.db-tTalk")
    public DataSource tTalkDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "datasource.db-openApi")
    public DataSource openApiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean tTalkEntityManagerFactory() {
        return builder.dataSource(tTalkDataSource()).packages(Department.class).persistenceUnit("tTalk").build();
    }

    @Bean
    public PlatformTransactionManager tTalkTransactionManager() {
        return new JpaTransactionManager(tTalkEntityManagerFactory().getObject());
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean openApiEntityManagerFactory() {
        return builder.dataSource(openApiDataSource()).packages(ClientToken.class).persistenceUnit("openapi").build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager openApiTransactionManager() {
        return new JpaTransactionManager(openApiEntityManagerFactory().getObject());
    }

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "tTalkEntityManagerFactory", basePackageClasses = DepartmentRepository.class, transactionManagerRef = "tTalkTransactionManager")
    static class TTalkDatasourceConfig {
    }

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "openApiEntityManagerFactory", basePackageClasses = ClientTokenRepository.class, transactionManagerRef = "openApiTransactionManager")
    static class OpenApiDatasourceConfig {

    }

    @Bean
    public SessionFactory sessionFactory() {
        if (tTalkEntityManagerFactory().getNativeEntityManagerFactory().unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return tTalkEntityManagerFactory().getNativeEntityManagerFactory().unwrap(SessionFactory.class);
    }
    */
}
