package com.tinderroulette.backend.rest.DBConnection;
import com.tinderroulette.backend.rest.model.App;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.model.Members;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class JsonReader {
    public static List<App> appReader(String url)throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(url), new TypeReference<List<App>>(){});
    }
    public static List<Members> memberReader(String url)throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(url), new TypeReference<List<Members>>(){});
    }
    public static List<MemberClass> memberClassReader(String url)throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(url), new TypeReference<List<MemberClass>>(){});
    }
}