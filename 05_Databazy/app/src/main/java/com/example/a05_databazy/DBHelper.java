package com.example.a05_databazy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {//more actions implement methods

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studenti";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_STUDENT = "CREATE TABLE " + MyContract.Student.TABLE_NAME + "("
                + MyContract.Student.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MyContract.Student.COLUMN_NAME + " TEXT, "
                + MyContract.Student.COLUMN_EMAIL + " TEXT, "
                + MyContract.Student.COLUMN_AGE + " INTEGER )";

        db.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// vymažeme tabuľku s týmto názvom ak existuje
        db.execSQL("DROP TABLE IF EXISTS " + MyContract.Student.TABLE_NAME);

        // vytvoríme tabuľku nanovo podľa štruktúry definovanej v onCreate
        onCreate(db);
    }

    public void addStudent(Student s) {
        // vyrobím mapu hodnôt – názov stĺpca + hodnota
        ContentValues values = new ContentValues();
        values.put(MyContract.Student.COLUMN_NAME, s.getMeno());
        values.put(MyContract.Student.COLUMN_EMAIL, s.getEmail());
        values.put(MyContract.Student.COLUMN_AGE, s.getVek());
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();
        // vložím novy riadok, pričom návratovou hodnotou je ID (primárny
        // kľúč), ktory dostal novovložený záznam
        long newRowId = db.insert(
                MyContract.Student.TABLE_NAME, // názov tabuľky
                null,    // ak použijeme FeedEntry.COLUMN_NAME_NULLABLE,
                // hodnoty prázdnych textových polí budú null,
                // ak použijeme null nebude v nich nič
                values); // definované páry pole - hodnota
        db.close(); // zavriem databazu
    }

    public Student getStudent(long ID) {
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();

        String[] projection = {MyContract.Student.COLUMN_NAME, // zoznam poli
                MyContract.Student.COLUMN_EMAIL,// na
                MyContract.Student.COLUMN_AGE}; // zobrazenie
        String selection = MyContract.Student.COLUMN_ID; // pole v podmienke
        String[] selectionArgs = {"" + ID};   // zoznam hodnôt pre podmienku

        Cursor c = db.query(
                MyContract.Student.TABLE_NAME, // tabuľka na dotaz
                projection,      // zoznam polí na vrátenie
                selection,       // WHERE bude nad týmto stĺpcom
                selectionArgs,   // hodnoty pre podmienku WHERE
                null,            // polia pre GROUP BY
                null,            // HAVING
                null);         // sort order

        Student s = new Student(ID,
                c.getString(c.getColumnIndex(MyContract.Student.COLUMN_NAME)),
                c.getString(2),
                c.getInt(c.getColumnIndex(MyContract.Student.COLUMN_AGE)));

        c.close();  // uzatvorenie kurzora
        db.close(); // uzatvorenie databazy
        return s;   // vráti naplnenú inštanciu študenta
    }

    public void deleteStudent(int ID) {
        SQLiteDatabase db = getWritableDatabase();
        // štandardný zápis
        db.delete(
                MyContract.Student.TABLE_NAME,    // názov tabuľky
                MyContract.Student.COLUMN_ID + "=" + ID,// podmienka
                null);            // zoznam parametrov

        // zápis s parametrami (bezpečnejší)
        db.delete(
                MyContract.Student.TABLE_NAME, // názov tabuľky
                MyContract.Student.COLUMN_ID + "= ?", // podmienka
                // s parametrom
                new String[]{"" + ID}); // hodnota parametra/-ov
        db.close();
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
                MyContract.Student.TABLE_NAME,    // názov tabuľky
                values,          // definovane pary pole - hodnota
                MyContract.Student.COLUMN_ID + "= ?", // podmienka
                new String[] { ""+s.getID() }); // parameter
        db.close(); // uzatvorenie databazy
    }

    public ArrayList<String> getStudentsNames() {
        // vytvorím zoznam stringov, ktorý naplním menami
        ArrayList<String> zoznam = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase(); // pristup k databaze
        Cursor c = db.rawQuery( // prečítam údaje z tabuľky
                "select * from " + MyContract.Student.TABLE_NAME, null);

        if (c.moveToFirst()) { // ak sa da posunut na zaciatok = neprazdny
            do { // budem pridavat do zoznamu
                //zoznam.add(c.getString(c.getColumnIndex(MyContract.Student.COLUMN_NAME)));
                zoznam.add(c.getString(c.getColumnIndex(MyContract.Student.COLUMN_ID)));
            } while (c.moveToNext()); // kym pojde posuvat na dalsi prvok
        }

        c.close();
        db.close(); // zatvorim kurzor a databazu
        return zoznam; // vratim zoznam
    }


}
