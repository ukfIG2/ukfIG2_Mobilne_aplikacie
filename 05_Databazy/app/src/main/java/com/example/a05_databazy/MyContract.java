package com.example.a05_databazy;

public class MyContract {

    public static class Student { //samostatne k tabulke
        public static final String TABLE_NAME = "students"; //stlpce k sql tabulke
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "meno";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_AGE = "vek";
    }



}
