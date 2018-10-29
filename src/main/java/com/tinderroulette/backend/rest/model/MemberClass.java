package com.tinderroulette.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tinderroulette.backend.rest.DBConnection.MemberClassDeserializer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "memberclass")
@IdClass(MemberClassId.class)
@JsonIgnoreProperties({"departement","cote_r","inscription", "programme", "unit_id","nom","prenom","trimestre_id","app"})
@JsonDeserialize(using = MemberClassDeserializer.class)
public class MemberClass {

    @Id
    @Column(name = "cip", columnDefinition="VARCHAR(8)")
    private String cip;

    @Id
    @Column(name = "id_class")
    private String idClass;

    public MemberClass() {
    }

    public MemberClass(String cip, String idClass) {
        this.cip = cip;
        this.idClass = idClass;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    private static class MemberClassId implements Serializable {
        private String cip;
        private String idClass;

        public MemberClassId(String cip, String idClass) {
            this.cip = cip;
            this.idClass = idClass;
        }

        public MemberClassId() {
        }

        public String getCip() {
            return cip;
        }

        public void setCip(String cip) {
            this.cip = cip;
        }

        public String getIdClass() {
            return idClass;
        }

        public void setIdClass(String idClass) {
            this.idClass = idClass;
        }
    }
}
