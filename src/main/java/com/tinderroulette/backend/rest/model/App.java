package com.tinderroulette.backend.rest.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tinderroulette.backend.rest.DBConnection.AppDeserializer;

@Entity
@Table(name = "app")
@JsonIgnoreProperties({"departement_id","groupe_id","inscription", "unite_id", "ap_id"})
@JsonDeserialize(using = AppDeserializer.class)

public class App {

    @Id
    @Column(name = "id_app")
    private String idApp;

    private String description;

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
