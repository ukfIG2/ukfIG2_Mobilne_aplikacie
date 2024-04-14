package com.example.a05_du_databaza;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;

public class Databaza extends SQLiteOpenHelper {

    private static final int Database_Version = 1;
    private static final String Database_Name = "Ziacka_knizka";

    public Databaza(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_STUDENT = "CREATE TABLE " + Student.TABLE_NAME + "("
                + Student.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Student.COLUMN_NAME + " TEXT, "
                + Student.COLUMN_SURNAME + " TEXT) ";

        db.execSQL(SQL_CREATE_TABLE_STUDENT);

        String SQL_CREATE_TABLE_PREDMET = "CREATE TABLE " + Predmet.TABLE_NAME + "("
                + Predmet.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Predmet.COLUMN_NAME + " TEXT) ";

        db.execSQL(SQL_CREATE_TABLE_PREDMET);

        String SQL_CREATE_TABLE_ZNAMKA = "CREATE TABLE " + Znamka.TABLE_NAME + "("
                + Znamka.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Znamka.COLUMN_PREDMET + " INTEGER, "
                + Znamka.COLUMN_STUDENT + " INTEGER, "
                + Znamka.COLUMN_ZNAMKA + " INTEGER, "
                + "FOREIGN KEY(" + Znamka.COLUMN_PREDMET + ") REFERENCES " + Predmet.TABLE_NAME + "(" + Predmet.COLUMN_ID + "), "
                + "FOREIGN KEY(" + Znamka.COLUMN_STUDENT + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.COLUMN_ID + "))";
        //System.out.println(SQL_CREATE_TABLE_ZNAMKA);
        db.execSQL(SQL_CREATE_TABLE_ZNAMKA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Pre jednoduche aplikacie takto
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Predmet.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Znamka.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void updateZnamka(long id, String znakma) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Znamka.COLUMN_ZNAMKA, Integer.parseInt(znakma));
        db.update(
                Znamka.TABLE_NAME,
                values,
                Znamka.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getStudents() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Student.COLUMN_ID + " AS _id",
                Student.COLUMN_NAME,
                Student.COLUMN_SURNAME
        };

        return db.query(
                Student.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null                   // The sort order
        );
    }

    public Cursor getPredmety() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Predmet.COLUMN_ID + " AS _id",
                Predmet.COLUMN_NAME
        };

        return db.query(
                Predmet.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getZnamky(long idStudent, long idPredmet) {
        SQLiteDatabase db = this.getReadableDatabase();


        String[] projection = {
                Znamka.COLUMN_ID + " AS _id",
               // Znamka.COLUMN_PREDMET,
                //Znamka.COLUMN_STUDENT,
                Znamka.COLUMN_ZNAMKA
        };

        String selection = Znamka.COLUMN_STUDENT + " = ? AND " + Znamka.COLUMN_PREDMET + " = ?";
        String[] selectionArgs = { String.valueOf(idStudent), String.valueOf(idPredmet) };

        Cursor c =  db.query(
                        Znamka.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
        );

        return c;
    }

    public Cursor getSelectZnamka(long idZnamka) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Znamka.COLUMN_PREDMET, Znamka.COLUMN_STUDENT, Znamka.COLUMN_ZNAMKA};
        String selection = Znamka.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(idZnamka)};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Znamka.TABLE_NAME);
        String query = builder.buildQuery(projection, selection, selectionArgs, null, null, null, null);

        Cursor cursor = db.query(
                Znamka.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor == null) {
            System.out.println("Cursor is null");
        } else if (cursor.getCount() == 0) {
            System.out.println("Cursor is empty");
        } else {
            System.out.println("Cursor has " + cursor.getCount() + " rows");
        }

        return cursor;
    }

    public String getStudentName(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Student.TABLE_NAME,
                new String[]{Student.COLUMN_NAME},
                Student.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Student.COLUMN_NAME));
            cursor.close();
            return name;
        }
        return null;
    }

    public String getStudentSurname(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Student.TABLE_NAME,
                new String[]{Student.COLUMN_SURNAME},
                Student.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(Student.COLUMN_SURNAME));
            cursor.close();
            return surname;
        }
        return null;
    }

    public String getPredmetName(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Predmet.TABLE_NAME,
                new String[]{Predmet.COLUMN_NAME},
                Predmet.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Predmet.COLUMN_NAME));
            cursor.close();
            return name;
        }
        return null;
    }

    public void addStudent(Student s) {
        SQLiteDatabase db = getWritableDatabase();
        //Ciste sql
        /*db.execSQL("INSERT INTO " + Student.TABLE_NAME + " (" + Student.COLUMN_NAME + ", " + Student.COLUMN_SURNAME + ") VALUES ('" + s.getName() + "', '" + s.getSurname() + "')");
        db.close();*/
        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_NAME, s.getName());
        values.put(Student.COLUMN_SURNAME, s.getSurname());
        db.insert(Student.TABLE_NAME,   //This is the name of the table where the new record will be inserted. In this case, it's the Student table.
                null,                   //If values is empty, this nullable string will define the name of the nullable column. In this case, it's set to null because values is not empty.
                values);                //This is a ContentValues object that contains the values to be inserted into the new row. In this case, it contains the name and surname of the new student..
        db.close();
    }

    public Student getStudent(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM " + Student.TABLE_NAME + " WHERE " + Student.COLUMN_ID + " = " + ID, null);
        //cursor.moveToFirst();
        //Student s = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        //cursor.close();
        //db.close();

        //This is the array of columns that will be returned by the query. In this case, it's an array of strings that contains the names of the columns that will be returned.
        String[] projection = {Student.COLUMN_NAME, Student.COLUMN_SURNAME};
        //This is the WHERE clause of the query. In this case, it's the name of the column that will be used in the WHERE clause.
        String selection = Student.COLUMN_ID + " = ?";
        //This is an array of strings that contains the values that will be used in the WHERE clause. In this case, it's an array that contains the ID of the student that will be returned.
        String[] selectionArgs = {String.valueOf(ID)};
        Cursor c = db.query(
                Student.TABLE_NAME,    //This is the name of the table that will be queried. In this case, it's the Student table.
                projection,            //This is an array of strings that contains the names of the columns that will be returned by the query.
                selection,             //This is the WHERE clause of the query. In this case, it's the name of the column that will be used in the WHERE clause.
                selectionArgs,         //This is an array of strings that contains the values that will be used in the WHERE clause.
                null,                  //This is an array of strings that contains the names of the columns that will be used in the GROUP BY clause.
                null,                  //This is the HAVING clause of the query.
                null);                 //This is the sort order of the query.

        //Skolsky sposob
       /* Student s = new Student(ID,
                c.getString(c.getColumnIndex(Student.COLUMN_NAME)),
                c.getString(c.getColumnIndex(Student.COLUMN_SURNAME)));*/

        if (c != null && c.moveToFirst()) {
            int nameIndex = c.getColumnIndex(Student.COLUMN_NAME);
            int surnameIndex = c.getColumnIndex(Student.COLUMN_SURNAME);

            if (nameIndex != -1 && surnameIndex != -1) {
                Student s = new Student(
                        c.getString(nameIndex),
                        c.getString(surnameIndex));

                c.close();
                db.close();
                return s;
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return null; // Add this line
    }

    public void deleteStudent(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                Student.TABLE_NAME,                     //This is the name of the table where the record will be deleted. In this case, it's the Student table.
                Student.COLUMN_ID + " = ?",             //This is the WHERE clause of the query. In this case, it's the ID of the student that will be deleted.
                new String[]{"" + ID});                 //This is an array of strings that contains the values that will be used in the WHERE clause.
        db.close();
    }

    public void updateStudent(Student s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_NAME, s.getName());
        values.put(Student.COLUMN_SURNAME, s.getSurname());
        db.update(
                Student.TABLE_NAME,
                values,
                Student.COLUMN_ID + " = ?",
                new String[]{"" + s.getId()});
        db.close();
    }

    public void addPredmet(Predmet p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Predmet.COLUMN_NAME, p.getName());
        db.insert(Predmet.TABLE_NAME,
                null,
                values);
        db.close();
    }

    public void deletePredmet(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                Predmet.TABLE_NAME,
                Predmet.COLUMN_ID + " = ?",
                new String[]{"" + ID});
        db.close();
    }

    public Predmet getPredmet(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = {Predmet.COLUMN_NAME};
        String selection = Predmet.COLUMN_ID + " = ?"; // Add " = ?" to provide a placeholder for the selection argument
        String[] selectionArgs = {String.valueOf(ID)};

        Cursor c = db.query(
                Predmet.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (c != null && c.moveToFirst()) {
            int nameIndex = c.getColumnIndex(Predmet.COLUMN_NAME);

            if (nameIndex != -1) {
                Predmet p = new Predmet(c.getString(nameIndex));

                c.close();
                db.close();
                return p;
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return null;
    }

    public void updatePredmet(Predmet p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Predmet.COLUMN_NAME, p.getName());
        db.update(
                Predmet.TABLE_NAME,
                values,
                Predmet.COLUMN_ID + " = ?",
                new String[]{"" + p.getId()});
        db.close();
    }

    public void addZnamka(Znamka z) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Znamka.COLUMN_PREDMET, z.getPredmet());
        values.put(Znamka.COLUMN_STUDENT, z.getStudent());
        values.put(Znamka.COLUMN_ZNAMKA, z.getZnamka());
        db.insert(Znamka.TABLE_NAME, null, values);
        db.close();

    }

    public void deleteZnamka(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                Znamka.TABLE_NAME,
                Znamka.COLUMN_ID + " = ?",
                new String[]{"" + ID});
        db.close();
    }

}
