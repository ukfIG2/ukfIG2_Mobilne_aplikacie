package com.example.a0507_videopozicovna;

import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SimpleCursorAdapter ca;
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NaplnDatabazu();
        NapojAdapter();
    }

    private void NaplnDatabazu() {
        dbh.addDVD( new DVD (1, "Winnetou", 1.0));
        dbh.addDVD( new DVD (2, "Anna zo zeleného domu", 0.5));
    }

    private void NapojAdapter() {
        ca = new SimpleCursorAdapter(this,
                R.layout.list_layout_dvd, // definovany layout
                dbh.VratKurzor(),	 // kurzor = ukazovateľ na udaje o studentoch
                new String[] { Contractor.DVD.COLUMN_ID,
                               Contractor.DVD.COLUMN_NAME,
                               Contractor.DVD.COLUMN_PRICE}, // zoznam from - názvy
                new int[] {R.id.textView, R.id.textView2, R.id.textView3}, // ciele
                0); // flag definuje spravanie adaptera, 0 je ok 
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(ca);
    }
}
