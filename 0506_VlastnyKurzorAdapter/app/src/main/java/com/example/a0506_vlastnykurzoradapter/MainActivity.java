package com.example.a0506_vlastnykurzoradapter;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    CustomCursorAdapter myAdapter;
    // získam inštanciu, ktorá má prístup k databáze
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //VlozStudentov();
        PripojAdapter();
    }

    private void PripojAdapter() {

        // získame kurzor na všetkých študentov – voláme rovnakú metódu ako
        // pre získanie kurzora pre SimpleCursorAdapter
        Cursor myCursor = dbh.VratKurzor();

        // nastavíme/prepojíme vlastný kurzor adapter na dáta
        // celé napojenie na prvky layoutu sa deje vo vnútri CCA
        myAdapter = new CustomCursorAdapter(this, myCursor, 0);

        // identifikujeme ListView do ktorého cheme dáta posielať
        ListView lvItems = (ListView) findViewById(R.id.listView);

        // napojíme naň adaptér
        lvItems.setAdapter(myAdapter);
    }


    private void VlozStudentov() {
        dbh.addStudent(new Student(1, "miso", "mm", 15));
        dbh.addStudent(new Student(3, "miska", "mmi", 21));
    }
}
