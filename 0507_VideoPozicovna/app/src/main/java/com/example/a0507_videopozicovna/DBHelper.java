package com.example.a0507_videopozicovna;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sinus on 29.03.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pozi221.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("","idem");
        String SQL_CREATE_TABLE = "CREATE TABLE " + Contractor.DVD.TABLE_NAME  + "("
                + Contractor.DVD.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contractor.DVD.COLUMN_NAME + " TEXT, "
                + Contractor.DVD.COLUMN_PRICE + " FLOAT) ";

        db.execSQL(SQL_CREATE_TABLE);
        Log.d("",SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contractor.DVD.TABLE_NAME);
        onCreate(db);
    }

    public void addDVD(DVD d) {
        ContentValues values = new ContentValues();
        values.put(Contractor.DVD.COLUMN_NAME, d.getNazov());
        values.put(Contractor.DVD.COLUMN_PRICE, d.getCena());
        // ziskam pristup k databaze
        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert(
                Contractor.DVD.TABLE_NAME, // názov tabuľky
                null,    // ak použijeme FeedEntry.COLUMN_NAME_NULLABLE,
                values); // definované páry pole - hodnota
        db.close(); // zavriem databazu
        Log.d("","vlozene" + newRowId);
    }

    public Cursor VratKurzor() {
        SQLiteDatabase db = getWritableDatabase();
        String sstr = "select * from "+Contractor.DVD.TABLE_NAME;
        Log.d("",sstr);
        Cursor c = db.rawQuery(sstr, null);
        // pre istotu môžeme doplniť nastavenie na začiatok
        c.moveToFirst();
        db.close();
        Log.d("","selectnute");
        return c;
    }


}
