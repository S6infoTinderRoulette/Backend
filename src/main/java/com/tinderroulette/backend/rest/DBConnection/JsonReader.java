package com.tinderroulette.backend.rest.DBConnection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tinderroulette.backend.rest.model.App;
import com.tinderroulette.backend.rest.model.Members;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class JsonReader {
    public static App[] appReader(String url)throws IOException{
        ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        return mapper.readValue(new URL(url), App[].class );
    }
    public static List<Members> memberReader(String url)throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        return mapper.readValue(new URL(url), new TypeReference<List<Members>>(){});
    }
}