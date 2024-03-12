package com.example.a04_06_aktivnylayout;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> studentList;
    StudentAdapter stAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        NaplnData();

        ListView lv = (ListView) findViewById(R.id.listView);

        stAdapter = new StudentAdapter(this, R.layout.list_layout, studentList);
        lv.setAdapter(stAdapter);
    }

    private void NaplnData () {
        studentList = new ArrayList<Student>();
        studentList.add(new Student("Jožko","C","B",false));
        studentList.add(new Student("Zuzka","A","A",false));
        studentList.add(new Student("Miško","B","E",false));
        studentList.add(new Student("Anička","E","B",false));
    }
}
