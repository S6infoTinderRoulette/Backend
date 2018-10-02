package com.tinderroulette.backend.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@RequestMapping("/")
    @ResponseBody
    String home() {
	    return "Hello world";
    }

    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    }
}
