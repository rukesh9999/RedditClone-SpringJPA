package com.ReditClone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

	@Bean
	public Docket redditCloneApi() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ReditClone.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
	
		return new ApiInfoBuilder()
				.title(" Reddit Clone Api")
				.version("1.0")
				.description("Api For Reddit Clone Apllication")
				.contact(new Contact("Rukesh", "http://programmingtechie.com", "rukesh235@gmail.com"))
				.license("Apache License Version 2.0")
				.build();
	}
}
