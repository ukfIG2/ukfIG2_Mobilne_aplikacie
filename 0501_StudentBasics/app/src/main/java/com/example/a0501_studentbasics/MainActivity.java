package com.example.a0501_studentbasics;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter myAdapter;
    // získam inštanciu, ktorá má prístup k databáze
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VlozStudentov();
        PripojAdapter();
    }

    private void VlozStudentov() {
        dbh.addStudent(new Student(1,"miso","mm",15));
        dbh.addStudent(new Student(1,"miska","mm@ukf.dk",21));
    }

    private void PripojAdapter() {
        // použijem vstavanú šablónu jednopoložkového listu
        myAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                dbh.getStudentsNames() ); // list<String> študentov
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
    }
}
