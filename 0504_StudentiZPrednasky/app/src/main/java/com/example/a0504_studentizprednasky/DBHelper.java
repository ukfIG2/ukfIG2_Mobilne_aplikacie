package com.example.a0504_studentizprednasky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // verzia  - kvôli upgradom
    private static final int DATABASE_VERSION = 5;
    // názov databázy – podľa neho sa vytvára databáza ak neexistuje
    // alebo otvára ak existuje
    private static final String DATABASE_NAME = "studenti";

    public DBHelper(Context context) { // konštruktor databázy
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // všetky potrebné tabuľky vytvárame tu
        // – konštanty ťaháme z tried definujúcich tabuľky

        String SQL_CREATE_TABLE_STUDENT = "CREATE TABLE " + MyContractor.Student.TABLE_NAME  + "("
                + MyContractor.Student.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MyContractor.Student.COLUMN_NAME + " TEXT, "
                + MyContractor.Student.COLUMN_EMAIL + " TEXT, "
                + MyContractor.Student.COLUMN_AGE + " INTEGER )";

        db.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers) {
        // vymažeme tabuľku s týmto názvom ak existuje
        db.execSQL("DROP TABLE IF EXISTS " + MyContractor.Student.TABLE_NAME);

        // vytvoríme tabuľku nanovo podľa štruktúry definovanej v onCreate
        onCreate(db);
    }

    public void addStudent(Student s) {
        // vyrobím mapu hodnôt – názov stĺpca + hodnota
        ContentValues values = new ContentValues();
        values.put(MyContractor.Student.COLUMN_NAME, s.getMeno());
        values.put(MyContractor.Student.COLUMN_EMAIL, s.getEmail());
        values.put(MyContractor.Student.COLUMN_AGE, s.getVek());
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();
        // vložím novy riadok, pričom návratovou hodnotou je ID (primárny
        // kľúč), ktory dostal novovložený záznam
        long newRowId = db.insert(
                MyContractor.Student.TABLE_NAME, // názov tabuľky
                null,    // ak použijeme FeedEntry.COLUMN_NAME_NULLABLE,
                // hodnoty prázdnych textových polí budú null,
                // ak použijeme null nebude v nich nič
                values); // definované páry pole - hodnota
        db.close(); // zavriem databazu
    }

    public Student getStudent(long ID) {
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();

        String[] projection = { MyContractor.Student.COLUMN_NAME, // zoznam poli
                MyContractor.Student.COLUMN_EMAIL,// na
                MyContractor.Student.COLUMN_AGE}; // zobrazenie
        String selection = MyContractor.Student.COLUMN_ID; // pole v podmienke
        String[] selectionArgs = {""+ID};   // zoznam hodnôt pre podmienku

        Cursor c = db.query(
                MyContractor.Student.TABLE_NAME, // tabuľka na dotaz
                projection,      // zoznam polí na vrátenie
                selection,       // WHERE bude nad týmto stĺpcom
                selectionArgs,   // hodnoty pre podmienku WHERE
                null,            // polia pre GROUP BY
                null,            // HAVING
                null  );         // sort order

        int index1 = c.getColumnIndex(MyContractor.Student.COLUMN_NAME);
        int index3 = c.getColumnIndex(MyContractor.Student.COLUMN_AGE);
        Student s = new Student(ID,
                c.getString(index1),
                c.getString(2),
                c.getInt(index3));

        c.close();  // uzatvorenie kurzora
        db.close(); // uzatvorenie databazy
        return s;   // vráti naplnenú inštanciu študenta
    }

    ArrayList<String> getStudentNames() {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<String> zoznam = new ArrayList<>();
        String[] projection = { MyContractor.Student.COLUMN_NAME};

        Cursor c = db.query(
                MyContractor.Student.TABLE_NAME, // tabuľka na dotaz
                projection,      // zoznam polí na vrátenie
                null,       // WHERE bude nad týmto stĺpcom
                null,   // hodnoty pre podmienku WHERE
                null,            // polia pre GROUP BY
                null,            // HAVING
                null );         // sort order

        if (c.moveToFirst()) { // ak sa da posunut na zaciatok = neprazdny
            do { // budem pridavat do zoznamu
                zoznam.add(c.getString(0));
            } while (c.moveToNext()); // kym pojde posuvat na dalsi prvok
        }

        c.close();
        db.close(); // zatvorim kurzor a databazu
        return zoznam; // vratim zoznam
    }

    public Cursor vratKurzor() {
        SQLiteDatabase db = getWritableDatabase();
        String sstr = "select * from "+MyContractor.Student.TABLE_NAME;
        Cursor c = db.rawQuery(sstr, null);
        // je potrebné nastaviť kurzor na začiatok
        c.moveToFirst();
        db.close();
        return c;
    }
}

