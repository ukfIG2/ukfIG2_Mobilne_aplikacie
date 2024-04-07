package com.example.a0506_vlastnykurzoradapter;

/**
 * Created by Sinus on 21.03.2017.
 */

public final class MyContract {

    /* Inner class that defines the table contents */
    public static class Student {
        public static final String TABLE_NAME = "students";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "meno";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_AGE = "vek";
    }
}