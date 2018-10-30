package com.tinderroulette.backend.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* Source (Spring Documentation) : https://spring.io/blog/2015/06/08/cors-support-in-spring-framework#javaconfig
* */

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**").allowedOrigins("http://localhost:8080"); }
	 */
}