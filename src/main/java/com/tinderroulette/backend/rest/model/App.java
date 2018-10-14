package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "app")
@JsonPropertyOrder({"ap_id", "app"})
@JsonIgnoreProperties({"departement_id","groupe_id","inscription", "profil_id", "trimestre_id", "unite_id"})
public class App {

    @Id
    @JsonAlias({ "ap_id", "idApp" }) private String idApp;
    @JsonAlias({ "app", "description" })private String description;

    public App(String idApp, String description) {
        this.idApp = idApp;
        this.description = description;
    }

    public App() {
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
