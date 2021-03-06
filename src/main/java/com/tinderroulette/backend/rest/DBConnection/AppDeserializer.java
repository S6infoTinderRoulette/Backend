package com.tinderroulette.backend.rest.DBConnection;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tinderroulette.backend.rest.model.App;

import java.io.IOException;

public class AppDeserializer extends JsonDeserializer<App> {
    @Override
    public App deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String profileId = node.get("profil_id").asText();
        String trimesterId = node.get("trimestre_id").asText();
        String appId = node.get("app").asText();
        String result = profileId+appId+trimesterId;
        return new App(result,null);
    }
}
