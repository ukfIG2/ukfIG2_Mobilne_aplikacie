package com.example.a05_du_databaza;

public class Predmet {

    public static final String TABLE_NAME = "predmety";
    public static final String COLUMN_ID = "idPredmet";
    public static final String COLUMN_NAME = "nazov";

    private long id;
    private String name;

    public Predmet(String name) {
        this.id = id;
        this.name = name;
    }

    public Predmet(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
