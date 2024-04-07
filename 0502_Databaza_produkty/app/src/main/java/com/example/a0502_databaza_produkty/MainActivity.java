package com.example.a0502_databaza_produkty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    DBHelper dbh = new DBHelper(this);
    ListView lv;
    ArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listview);
        //loadData();
        addAdapter();
    }

    private void loadData() {
        dbh.addProduct(new Product(1,"Corsair K57","Klávesnica",23));
        dbh.addProduct(new Product(2,"Razer Wireless","Myška",47));
        dbh.addProduct(new Product(3,"Logitech G57","Headset",12));

        dbh.updateProduct(new Product(3,"Logitech G57","Headset",62));

    }

    private void addAdapter() {
        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,dbh.getProductsAsList());
        lv.setAdapter(myAdapter);
    }

}