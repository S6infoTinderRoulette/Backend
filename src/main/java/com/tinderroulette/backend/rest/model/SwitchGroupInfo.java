package com.tinderroulette.backend.rest.model;

public class SwitchGroupInfo {

    private int idGroup;

    private String cip;

    private String idClass;

    private int groupIndex;

    private int groupIndexInit;

    public SwitchGroupInfo() {

    }

    public SwitchGroupInfo(int idGroup, String cip, String idClass, int groupIndex, int groupIndexInit) {
        this.idGroup = idGroup;
        this.cip = cip;
        this.idClass = idClass;
        this.groupIndex = groupIndex;
        this.groupIndexInit = groupIndexInit;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
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

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public int getGroupIndexInit() {
        return groupIndexInit;
    }

    public void setGroupIndexInit(int groupIndexInit) {
        this.groupIndexInit = groupIndexInit;
    }

}
