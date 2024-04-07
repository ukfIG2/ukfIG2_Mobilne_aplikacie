package com.example.a0506_vlastnykurzoradapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sinus on 21.03.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    // verzia  - kvôli upgradom
    private static final int DATABASE_VERSION = 2;
    // názov tabuľky – podľa neho sa vytvára databáza ak neexistuje
    // alebo otvára ak existuje
    private static final String DATABASE_NAME = "studenti";

    public DBHelper(Context context) { // konštruktor databázy
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // všetky potrebné tabuľky vytvárame tu
        // – konštanty ťaháme z tried definujúcich tabuľky
        String SQL_CREATE_TABLE_STUDENT = "CREATE TABLE " + MyContract.Student.TABLE_NAME  + "("
                + MyContract.Student.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MyContract.Student.COLUMN_NAME + " TEXT, "
                + MyContract.Student.COLUMN_EMAIL + " TEXT, "
                + MyContract.Student.COLUMN_AGE + " INTEGER )";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        // vymažeme tabuľku s týmto názvom ak existuje
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContract.Student.TABLE_NAME);

        // vytvoríme tabuľku nanovo podľa štruktúry definovanej v onCreate
        onCreate(sqLiteDatabase);
    }

    public void addStudent(Student s) {
        // vyrobím mapu hodnôt – názov stĺpca + hodnota
        ContentValues values = new ContentValues();
        values.put(MyContract.Student.COLUMN_NAME, s.getMeno());
        values.put(MyContract.Student.COLUMN_EMAIL, s.getEmail());
        values.put(MyContract.Student.COLUMN_AGE, s.getVek());

        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();
        // vložím nov riadok, pričom návratovou hodnotou je ID (primárny
        // kľúč), ktoré dostal novovložený záznam
        long newRowId = db.insert(
                MyContract.Student.TABLE_NAME, // názov tabuľky
                null,    // ak použijeme FeedEntry.COLUMN_NAME_NULLABLE,
                // hodnoty prázdnych textových polí budú null,
                // ak použijeme null nebude v nich nič
                values); // definované páry pole - hodnot
        db.close();
    }

    public Student getStudent(long ID) {
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();

        // zoznam poli na ziskanie
        String[] projection= {MyContract.Student.COLUMN_NAME,MyContract.Student.COLUMN_EMAIL, MyContract.Student.COLUMN_AGE};
        String selection = MyContract.Student.COLUMN_ID;        // pole v podmienke (WHERE)
        String[] selectionArgs = {""+ID};         // zoznam hodnôt pre podm.

        Cursor c = db.query(
                MyContract.Student.TABLE_NAME,   // tabuľka na dotazovanie
                projection,      // zoznam polí na vrátenie
                selection,       // WHERE bude nad týmto stĺpcom
                selectionArgs,   // hodnoty pre podmienku WHERE
                null,            // polia pre GROUP BY
                null,            // HAVING
                null  );         // sort order

        Student s = new Student(ID,
                c.getString(c.getColumnIndex(MyContract.Student.COLUMN_NAME)),
                c.getString(2),
                c.getInt(c.getColumnIndex(MyContract.Student.COLUMN_AGE)));
        c.close();  // uzatvorenie kurzora
        db.close(); // uzatvorenie databazy
        return s;   // vráti naplnenú inštanciu študenta
    }

    public void deleteStudent(int ID) {
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();

        // zápis s parametrami (bezpečnejší)
        db.delete(
                MyContract.Student.TABLE_NAME,  	   // názov tabuľky
                MyContract.Student.COLUMN_ID + "= ?",  // podmienka s parametrom
                new String[] { ""+ID }); // hodnota parametrov
        db.close(); // uzatvorenie databazy
    }

    public void updateStudent(Student s) {
        // vyrobím mapu hodnôt – názov stĺpca + hodnota
        ContentValues values = new ContentValues();
        values.put(MyContract.Student.COLUMN_NAME, s.getMeno());
        values.put(MyContract.Student.COLUMN_EMAIL, s.getEmail());
        values.put(MyContract.Student.COLUMN_AGE, s.getVek());

        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();
        db.update(
                MyContract.Student.TABLE_NAME,  	   // názov tabuľky
                values,  // definovane pary pole - hodnota
                MyContract.Student.COLUMN_ID + "= ?",  // podmienka s parametrom
                new String[] { ""+s.getID() }); // hodnota parametrov_
        db.close(); // uzatvorenie databazy
    }

    public Cursor VratKurzor() {
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();
        String sstr = "select * from "+MyContract.Student.TABLE_NAME;

        Cursor c = db.rawQuery(sstr, null);
        // pre istotu môžeme doplniť nastavenie na začiatok
        c.moveToFirst();
        db.close();
        return c;
    }



}
