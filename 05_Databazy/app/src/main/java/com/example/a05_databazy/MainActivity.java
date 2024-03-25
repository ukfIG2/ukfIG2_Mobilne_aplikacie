package com.example.a05_databazy;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DBHelper dbh = new DBHelper(this);
    ArrayAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
VlozStudenta();
        PripojAdapter();


    }

    public void VlozStudenta() {
        dbh.addStudent(new Student(1, "miso", "mm", 15));
        dbh.addStudent(new Student(2, "zuza", "mm", 17));
        dbh.addStudent(new Student(3, "fero", "mm", 16));
        // tu sa pre dane ID zmeni obsah zaznamu podla poslanych hodnot
        dbh.updateStudent(new Student(3, "miska", "mm", 14));
        dbh.addStudent(new Student(2, "gabo", "mm", 22));
    }

    private void PripojAdapter() {
        // použijem vstavanú šablónu jednopoložkového listu
        myAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                dbh.getStudentsNames() ); // list<String> študentov
        // z DBHelpera

        ListView lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(myAdapter);
    }

}