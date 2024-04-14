package com.example.a05_du_databaza;

public class Znamka {

    public static final String TABLE_NAME = "znamky";
    public static final String COLUMN_ID = "idZnamka";
    public static final String COLUMN_PREDMET = "predmet";
    public static final String COLUMN_STUDENT = "student";
    public static final String COLUMN_ZNAMKA = "znamka";

    private long id;
    private long predmet;
    private long student;
    private int znamka;

    public Znamka(long predmet, long student, int znamka) {
        this.id = id;
        this.predmet = predmet;
        this.student = student;
        this.znamka = znamka;

    }

    public long getId() {
        return id;
    }

    public long getPredmet() {
        return predmet;
    }

    public void setPredmet(int predmet) {
        this.predmet = predmet;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public int getZnamka() {
        return znamka;
    }

    public void setZnamka(int znamka) {
        this.znamka = znamka;
    }
}
