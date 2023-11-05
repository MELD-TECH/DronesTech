package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
@EnableCaching
public class SpringRedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisDemoApplication.class, args);
	}

}
