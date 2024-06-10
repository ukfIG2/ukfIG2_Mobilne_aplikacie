package com.example.skuska_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Databaza_lokalna extends SQLiteOpenHelper {

    private static final int Database_Version = 1;
    private static final String Database_Name = "Kniznica";

    public Databaza_lokalna(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_ZANER_TABLE = "CREATE TABLE zaner (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "zaner TINYTEXT" +
                ");";

        String CREATE_KNIHA_TABLE = "CREATE TABLE kniha (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nazov TINYTEXT," +
                "autor TINYTEXT," +
                "rok YEAR," +
                "obsah TEXT," +
                "zaner INTEGER," +
                "FOREIGN KEY(zaner) REFERENCES zaner(id)" +
                ");";

        db.execSQL(CREATE_ZANER_TABLE);
        db.execSQL(CREATE_KNIHA_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS zaner");
        db.execSQL("DROP TABLE IF EXISTS kniha");
        onCreate(db);
    }

    public Cursor getKnihy() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                kniha.COLUMN_ID,
                kniha.COLUMN_NAZOV,
                kniha.COLUMN_AUTOR,
                kniha.COLUMN_ROK,
                kniha.COLUMN_OBSAH,
                kniha.COLUMN_ZANER
        };

        String sortOrder = kniha.COLUMN_NAZOV + " ASC";


        return db.query(
                kniha.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }


    public void addKniha(kniha kniha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(kniha.COLUMN_NAZOV, kniha.getNazov());
        values.put(kniha.COLUMN_AUTOR, kniha.getAutor());
        values.put(kniha.COLUMN_ROK, kniha.getRok());
        values.put(kniha.COLUMN_OBSAH, kniha.getObsah());
        values.put(kniha.COLUMN_ZANER, kniha.getZaner_id());
        db.insert(kniha.TABLE_NAME, null, values);
        db.close();
    }

    public void addZaner(zaner zaner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(zaner.COLUMN_ZANER, zaner.getZaner());
        db.insert(zaner.TABLE_NAME, null, values);
        db.close();
    }

    public List<zaner> getAllZaner() {
        List<zaner> zanerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                zaner.COLUMN_ID,
                zaner.COLUMN_ZANER
        };

        Cursor cursor = db.query(
                zaner.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                zaner zaner = new zaner();
                zaner.setId(cursor.getInt(cursor.getColumnIndex(zaner.COLUMN_ID)));
                zaner.setZaner(cursor.getString(cursor.getColumnIndex(zaner.COLUMN_ZANER)));
                zanerList.add(zaner);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return zanerList;
    }

    public kniha getKniha(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                kniha.COLUMN_ID,
                kniha.COLUMN_NAZOV,
                kniha.COLUMN_AUTOR,
                kniha.COLUMN_ROK,
                kniha.COLUMN_OBSAH,
                kniha.COLUMN_ZANER
        };

        String selection = kniha.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                kniha.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            long bookId = cursor.getLong(cursor.getColumnIndex(kniha.COLUMN_ID));
            String nazov = cursor.getString(cursor.getColumnIndex(kniha.COLUMN_NAZOV));
            String autor = cursor.getString(cursor.getColumnIndex(kniha.COLUMN_AUTOR));
            int rok = cursor.getInt(cursor.getColumnIndex(kniha.COLUMN_ROK));
            String obsah = cursor.getString(cursor.getColumnIndex(kniha.COLUMN_OBSAH));
            long zaner = cursor.getLong(cursor.getColumnIndex(kniha.COLUMN_ZANER));
            cursor.close();

            return new kniha(bookId, nazov, autor, rok, obsah, zaner);
        } else {
            return null;
        }
    }

    public void updateKniha(kniha kniha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(kniha.COLUMN_NAZOV, kniha.getNazov());
        values.put(kniha.COLUMN_AUTOR, kniha.getAutor());
        values.put(kniha.COLUMN_ROK, kniha.getRok());
        values.put(kniha.COLUMN_OBSAH, kniha.getObsah());
        values.put(kniha.COLUMN_ZANER, kniha.getZaner_id());

        String selection = kniha.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(kniha.getId())};

        db.update(kniha.TABLE_NAME, values, selection, selectionArgs);
    }

    public void deleteKniha(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = kniha.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(kniha.TABLE_NAME, selection, selectionArgs);
    }

    public List<kniha> getAllKniha() {
        List<kniha> knihaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                kniha.COLUMN_ID,
                kniha.COLUMN_NAZOV,
                kniha.COLUMN_AUTOR,
                kniha.COLUMN_ROK,
                kniha.COLUMN_OBSAH,
                kniha.COLUMN_ZANER
        };

        Cursor cursor = db.query(
                kniha.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(kniha.COLUMN_ID));
                String nazov = cursor.getString(cursor.getColumnIndex(kniha.COLUMN_NAZOV));
                String autor = cursor.getString(cursor.getColumnIndex(kniha.COLUMN_AUTOR));
                int rok = cursor.getInt(cursor.getColumnIndex(kniha.COLUMN_ROK));
                String obsah = cursor.getString(cursor.getColumnIndex(kniha.COLUMN_OBSAH));
                long zaner = cursor.getLong(cursor.getColumnIndex(kniha.COLUMN_ZANER));
                kniha kniha = new kniha(id, nazov, autor, rok, obsah, zaner);
                knihaList.add(kniha);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return knihaList;
    }




}
