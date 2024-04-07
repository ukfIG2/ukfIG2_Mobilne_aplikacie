package com.example.a0504_studentizprednasky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);
    ArrayAdapter<String> aa;
    SimpleCursorAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pridajStudentov();
        pripojAdapter();
    }

    private void pripojAdapter() {
/*
        aa = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dbh.getStudentNames()
        );
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(aa);
    */
        myAdapter = new SimpleCursorAdapter(
                this,
                R.layout.simple_layout, // definovany layout
                dbh.vratKurzor(),	 // kurzor = ukazovateľ na udaje o studentoch
                // = na dotaz
                new String[] { MyContractor.Student.COLUMN_ID,
                        MyContractor.Student.COLUMN_NAME,
                        MyContractor.Student.COLUMN_EMAIL,
                        MyContractor.Student.COLUMN_AGE}, // zoznam from - názvy
                new int[] {R.id.id1, R.id.name1, R.id.age1, R.id.email1}, // ciele
                0); // flag definuje spravanie adaptera, 0 je ok 

        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
    }

    private void pridajStudentov() {
        // id v tychto troch su nepodstatne
        // pri vkladani ich nepouzivame
        dbh.addStudent(new Student(1, "miso", "mm", 15));
        dbh.addStudent(new Student(2, "zuza", "mm", 17));
        dbh.addStudent(new Student(3, "fero", "mm", 16));
    }
}