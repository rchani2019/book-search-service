package com.dev.rest.config;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.dev.rest.data.User;
import com.dev.rest.data.repository.UserRepository;

@Configuration
public class DatasourceConfig {
    
	@Autowired
    EntityManagerFactoryBuilder builder;
	
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return builder.dataSource(dataSource()).packages(User.class).persistenceUnit("test").build();
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
    
    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackageClasses = UserRepository.class, transactionManagerRef = "transactionManager")
    static class datasourceConfig {
    }
    
    @Bean
    public SessionFactory sessionFactory() {
        if (entityManagerFactory().getNativeEntityManagerFactory().unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManagerFactory().getNativeEntityManagerFactory().unwrap(SessionFactory.class);
    }
}
