package com.example.a0508_hotely;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sinus on 30.03.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ubytovne";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) { // konštruktor databázy
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      String sql = "CREATE TABLE " + MyContract.Hotely.TABLE_NAME  + "("
              + MyContract.Hotely.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
              + MyContract.Hotely.NAME + " TEXT, "
              + MyContract.Hotely.CAPACITY + " INTEGER ) ";

      Log.d("sql",sql);
      db.execSQL(sql);
      Log.d("sql","done");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // vymažeme tabuľku s týmto názvom ak existuje
      db.execSQL("DROP TABLE IF EXISTS " + MyContract.Hotely.TABLE_NAME);
      // vytvoríme tabuľku nanovo podľa štruktúry definovanej v onCreate
      onCreate(db);
    }

    public void addHotel(Hotel h) {
        ContentValues data = new ContentValues();
        data.put(MyContract.Hotely.NAME, h.getNazov());
        data.put(MyContract.Hotely.CAPACITY, h.getKapacita());

        SQLiteDatabase db = getWritableDatabase();
        long ID = db.insert(
                MyContract.Hotely.TABLE_NAME,
                null,
                data);
        db.close();
    }

    public Cursor VratKurzor() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + MyContract.Hotely.TABLE_NAME;
        Cursor c =  db.rawQuery(sql,null);
        c.moveToFirst();
        db.close();
        return c;
    }
}
