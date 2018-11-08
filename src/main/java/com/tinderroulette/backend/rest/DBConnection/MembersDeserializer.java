package com.tinderroulette.backend.rest.DBConnection;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tinderroulette.backend.rest.model.Members;

import java.io.IOException;

public class MembersDeserializer extends JsonDeserializer<Members> {
    @Override
    public Members deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String cip = node.get("cip_etudiant").asText(); //VARCHAR(8)
        int idMemberStatus = 1; //student
        String lastName = node.get("nom").asText();
        String firstName = node.get("prenom").asText();

        String email;
        if(node.get("courriel")!=null) email = node.get("courriel").asText();
        else email = cip+"@usherbrooke.ca";

        return new Members(cip,idMemberStatus,lastName,firstName,email);
    }

}
