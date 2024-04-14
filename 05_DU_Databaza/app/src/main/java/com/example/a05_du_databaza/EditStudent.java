package com.example.a05_du_databaza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditStudent extends AppCompatActivity {

    private Intent intent;
    private EditText meno;
    private EditText priezvisko;
    private Button edit;
    private Button exit;
    private long id;
    private Databaza dbh = new Databaza(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_student);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        meno = findViewById(R.id.EditPredmet_Meno);
        priezvisko = findViewById(R.id.EditStudent_Priezvisko);
        edit = findViewById(R.id.EditPredmet_Edit);
        exit = findViewById(R.id.EditPredmet_Koniec);
        edit.setOnClickListener(e -> editStudent());
        exit.setOnClickListener(e -> finish());

        id = intent.getLongExtra("idStudent", 0);
        nacitajUdaje();


    }

    private void nacitajUdaje(){
        Student student = dbh.getStudent(id);
        meno.setText(student.getName());
        priezvisko.setText(student.getSurname());
    }

    private void editStudent(){
        Student student = new Student(id, meno.getText().toString(), priezvisko.getText().toString());
        dbh.updateStudent(student);
        finish();
    }



}