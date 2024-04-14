package com.example.a05_du_databaza;

import android.widget.Toolbar;

public class Student {

    public static final String TABLE_NAME = "students";
    public static final String COLUMN_ID = "idStudent";
    public static final String COLUMN_NAME = "meno";
    public static final String COLUMN_SURNAME = "priezvisko";


    private long id;
    private String name;
    private String surname;

    public Student(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;

    }

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String toString() {
        return name + " " + surname;
    }
}
