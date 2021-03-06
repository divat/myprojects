package com.cm.style.profile.config.swagger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact(
			"Zinio Dev Team", "", "dhivakart@codemantra.co.in, thiyagarajan@codemantra.com,rajarajan@codemantra.in,sabareesan@codemantra.co.in");
	
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Zinio - Style Profile REST API And Monitoring", "Style profiling API for zinio", 
			"1.0", "", DEFAULT_CONTACT, "Zinio", "http://www.codemantra.com");
	
	public static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = 
			new HashSet<String>(Arrays.asList("application/json"));
	
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES)
				.select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(Predicates.not(PathSelectors.regex("/error.*")))
	            .build();
	}
}