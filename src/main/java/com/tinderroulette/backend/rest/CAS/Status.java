package com.tinderroulette.backend.rest.CAS;

public enum Status {
    Other(0), Student(1), Teacher(2), Support(3), Admin(4);
    private final int id;

    public int getId() {
        return id;
    }

    private Status(int id) {
        this.id = id;
    }

}