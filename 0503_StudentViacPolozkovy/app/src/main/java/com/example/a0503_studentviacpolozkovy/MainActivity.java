package com.example.a0503_studentviacpolozkovy;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SimpleAdapter myAdapter;
    // získam inštanciu, ktorá má prístup k databáze
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //VlozStudentov();
        PripojAdapter();
    }

    private void VlozStudentov() {
        dbh.addStudent(new Student(1, "miso", "mm", 15));
        dbh.addStudent(new Student(3, "miska", "mmi", 21));
    }

    private void PripojAdapter() {
            myAdapter = new SimpleAdapter(
                    this,
                    dbh.getStudents(),// ziskame zoanam map = potrebne udaje o studentoch
                    R.layout.list_layout, // udaje vkladame do tohto layoutu
                    new String[] { "id","name","age"}, // nazvy klucov/premennych
                    // z vytvorenej mapy
                    new int[] {R.id.id1, R.id.name1, R.id.age1} // obsah prislusnych
                    // premennych pride
                    // do tychto prvkov
            );

            ListView lv= (ListView)findViewById(R.id.listView);
            lv.setAdapter(myAdapter);
    }

}
