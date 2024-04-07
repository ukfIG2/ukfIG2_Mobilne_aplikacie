package com.example.a0508_hotely;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SimpleCursorAdapter myAdapter;
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VlozData();
        NapojAdapter();
    }

    private void VlozData() {
        dbh.addHotel( new Hotel(1,"Hilton", 500));
        dbh.addHotel( new Hotel(0,"Hotel Kempinsky", 100));
        dbh.addHotel( new Hotel(1,"Veľký hotel pre malých ľudí", 5000));
    }

    private void NapojAdapter() {
        myAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_layout_hotel,
                dbh.VratKurzor(),
                new String[] {MyContract.Hotely.NAME, MyContract.Hotely.CAPACITY},
                new int[] {R.id.textView, R.id.textView2},
                0
        );
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
    }

}
