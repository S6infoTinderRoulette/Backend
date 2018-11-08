package com.tinderroulette.backend.rest;

import java.text.MessageFormat;

public enum Message {
    // CRUD messages
    ACTIVITY_EXIST("L'Activities correspondante est déjà présente dans la base de données"),
    ACTIVITY_NOT_EXIST("L'Activities correspondante n'est pas présente dans la base de données"),
    AP_EXIST("L'Ap correspondante est déjà présente dans la base de données"),
    AP_NOT_EXIST("L'Ap correspondante n'est pas présente dans la base de données"),
    APP_EXIST("L'App correspondante est déjà présente dans la base de données"),
    APP_NOT_EXIST("L'App correspondante n'est pas présente dans la base de données"),
    CLASS_EXIST("La Classes correspondante est déjà présente dans la base de données"),
    CLASS_NOT_EXIST("La Classes correspondante n'est pas présente dans la base de données"),
    GROUP_EXIST("Le Groups correspondant est déjà présent dans la base de données"),
    GROUP_NOT_EXIST("Le Groups correspondant n'est pas présent dans la base de données"),
    GROUPSTUDENT_EXIST("Le groupStudent correspondant est déjà présent dans la base de données"),
    GROUPSTUDENT_NOT_EXIST("Le GroupStudent correspondant n'est pas présent dans la base de données"),
    GROUPTYPE_EXIST("Le GroupType correspondant au groupType : {0} est déjà présent dans la base de données"),
    GROUPTYPE_NOT_EXIST("Le GroupType correspondant au groupType : {0} est déjà présent dans la base de données"),
    MEMBERCLASS_EXIST("Le membreclass correspondant est déjà présent dans la base de données"),
    MEMBERCLASS_NOT_EXIST("Le membreclass correspondant n'est pas présent dans la base de données"),
    MEMBER_EXIST("Le membre correspondant au CIP : {0} est déjà présent dans la base de données"),
    MEMBER_NOT_EXIST("Le membre correspondant au CIP : {0} n'est pas présent dans la base de données"),
    MEMBERSTATUS_EXIST("Le MemberStatus correspondant à l'Id  : {0} est déjà présent dans la base de données"),
    MEMBERSTATUS_NOT_EXIST("Le MemberStatus correspondant à l'Id  : {0} n'est pas présent dans la base de données"),
    REQUEST_EXIST("La Request correspondante est déjà présente dans la base de données"),
    REQUEST_NOT_EXIST("La Request correspondante n'est pas présente dans la base de données"),
    SWITCHREQUEST_EXIST("La switchGroupRequest correspondante est déjà présente dans la base de données"),
    SWITCHREQUEST_NOT_EXIST("La switchGroupRequest correspondante n'est pas présente dans la base de données"),

    // Matchmaking messages
    MATCH_SUCCESS("{0} et {1} sont maintenant en équipe"),

    // SWITCHREQUEST messages
    SWITCH_SUCCESS("{0} et {1} ont échangé de tutorat"),

    // Partitionneur messages
    PARTITIONNEUR_PARAM_ERROR("Paramètres de post incohérents"),
    PARTITIONNEUR_SUM_EXCEED("La somme des tailles des groupe est inférieure au nombre d'étudiants inscrits !"),
    PARTITIONNEUR_TUTORAT_EXIST("Ce tutorat existe déjà dans la base de données !");
    private final String message;

    private Message(String message) {
        this.message = message;
    }

    public boolean equalsName(String otherName) {
        return message.equals(otherName);
    }

    public static String format(Message message, Object... args) {
        return MessageFormat.format(message.message, args);
    }

    public String toString() {
        return this.message;
    }
}