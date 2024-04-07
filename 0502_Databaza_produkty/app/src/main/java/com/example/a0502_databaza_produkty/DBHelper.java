package com.example.a0502_databaza_produkty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "myeshop";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE products
        // product_name TEXT
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + MyContract.Product.TABLE_NAME + "("
        + MyContract.Product.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + MyContract.Product.PRODUCT_NAME + " TEXT, "
        + MyContract.Product.PRODUCT_CATEGORY + " TEXT,"
        + MyContract.Product.PRODUCT_SKU + " INTEGER)";

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + MyContract.Product.TABLE_NAME;
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }


    public void addProduct(Product p) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Product.PRODUCT_NAME,p.getProduct_name());
        values.put(MyContract.Product.PRODUCT_CATEGORY,p.getProduct_category());
        values.put(MyContract.Product.PRODUCT_SKU,p.getProduct_sku());

        SQLiteDatabase db = getWritableDatabase();
        //INSERT INTO PRODUCTS
        long newID = db.insert(MyContract.Product.TABLE_NAME,null,values);
        db.close();
    }

    public Product getProduct(long ID) { // ID = 1
        // PRÍSTUP K DATABÁZE
        SQLiteDatabase db = getWritableDatabase();
        // SELECT product_name, product_category, product_sku FROM products WHERE _id = 1
        // SELECT product_name, product_category, product_sku FROM products WHERE _id = 1
        //SELECT
        String[] projection = {MyContract.Product.PRODUCT_NAME,
                               MyContract.Product.PRODUCT_CATEGORY,
                                MyContract.Product.PRODUCT_SKU};

        String selection = MyContract.Product.PRODUCT_ID;
        String[] selectionArgs = {"" +ID};

        Cursor c =  db.query(MyContract.Product.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        Product p = new Product(ID,c.getString(c.getColumnIndex(MyContract.Product.PRODUCT_NAME)),
                                    c.getString(c.getColumnIndex(MyContract.Product.PRODUCT_CATEGORY)),
                                    c.getInt(c.getColumnIndex(MyContract.Product.PRODUCT_SKU)));

        c.close();
        db.close();
        return p;
    }

    public void deleteProduct(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MyContract.Product.TABLE_NAME,MyContract.Product.PRODUCT_ID + "=" + ID,null);
        //db.delete(MyContract.Product.TABLE_NAME,MyContract.Product.PRODUCT_ID + "= ?",new String[] {""+ID});
        db.close();
    }

    public void updateProduct(Product p) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Product.PRODUCT_NAME,p.getProduct_name());
        values.put(MyContract.Product.PRODUCT_CATEGORY,p.getProduct_category());
        values.put(MyContract.Product.PRODUCT_SKU,p.getProduct_sku());

        SQLiteDatabase db = getWritableDatabase();
        db.update(MyContract.Product.TABLE_NAME,values,MyContract.Product.PRODUCT_ID + "="+p.getID(),null);
        db.close();
    }

    public ArrayList<String> getProductsAsList() {
        ArrayList<String> zoznam = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+MyContract.Product.TABLE_NAME,null);
        if(c.moveToFirst()) {
            // zoznam[0] = "Klávesnica, PC Doplnky, 20"
            do {
                zoznam.add(c.getString(c.getColumnIndex(MyContract.Product.PRODUCT_NAME))+", " +
                           c.getString(c.getColumnIndex(MyContract.Product.PRODUCT_CATEGORY)) + ", " +
                           c.getString(c.getColumnIndex(MyContract.Product.PRODUCT_SKU)));

            }while(c.moveToNext());
        }

        c.close();
        db.close();
        return zoznam;
    }

}
