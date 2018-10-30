package com.tinderroulette.backend.rest;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;


import java.io.IOException;



@Controller
@SpringBootApplication
@EnableScheduling
@ComponentScan
public class Application
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main( String[] args ) throws IOException {
        SpringApplication.run(Application.class, args);
    }

}
