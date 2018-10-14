package com.tinderroulette.backend.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.model.App;
import com.tinderroulette.backend.rest.model.Members;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.tinderroulette.backend.rest.DBConnection.JsonReader.appReader;
import static com.tinderroulette.backend.rest.DBConnection.JsonReader.memberReader;

@Controller
@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main( String[] args ) throws IOException {
    	SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(MembersDao repository) throws IOException{
        return (args) -> {
            //App[] apps = appReader("http://zeus.gel.usherbrooke.ca:8080/ms/rest/app_ap?inscription=2018-01-01");
            List<Members> members = memberReader("http://zeus.gel.usherbrooke.ca:8080/ms/rest/etudiant_groupe?inscription=2018-01-01&trimestre_id=A18");

            // not working: need to add the idMemberStatus field ourselves because doesn't exist in json
            repository.save(members);
        };
    }
}
