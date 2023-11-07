package com.example.demo.service;


import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManager;



@Configuration
public class AppConfig {

	@Profile("test")
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.h2.Driver");
	    dataSource.setUrl("jdbc:h2:mem:dronedb");
	    dataSource.setUsername("sa");
	    dataSource.setPassword("password");
	    dataSource.setConnectionProperties(additionalProperties());
	    return dataSource;
	}

	@Profile("test")
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	    em.setDataSource(dataSource());
	    em.setPackagesToScan("com.example.demo.model");
	    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	    return em;
	}
	
	@Profile("test")
	@Bean
	public EntityManager entityManager() {
	    return entityManagerFactory().getObject().createEntityManager();
	}
	
	@Profile("test")
	Properties additionalProperties() {
	    Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	       
	    return properties;
	}
}
