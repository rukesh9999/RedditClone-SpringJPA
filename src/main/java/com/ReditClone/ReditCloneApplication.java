package com.ReditClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ReditClone.config.SwaggerConfiguration;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
@EnableTransactionManagement
@Import(SwaggerConfiguration.class)
@EnableCaching
public class ReditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReditCloneApplication.class, args);
	}

}

