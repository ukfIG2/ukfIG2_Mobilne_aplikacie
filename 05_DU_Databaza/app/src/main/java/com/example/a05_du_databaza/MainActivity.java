package com.example.a05_du_databaza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Databaza dbh = new Databaza(this);

    private SimpleCursorAdapter myAdapter;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_RUN = "FirstRun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbh = new Databaza(this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean(FIRST_RUN, true)) {
            dummyData();
            settings.edit().putBoolean(FIRST_RUN, false).apply();
        }

        pripojAdapter();
        pridejListener();
        pridajDlhyListener();

        Toolbar tb = findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
    }

    private void pripojAdapter(){
      myAdapter = new SimpleCursorAdapter(this,
                R.layout.list_layout_main,
                dbh.getStudents(),
                new String[] {  "_id",
                                Student.COLUMN_NAME,
                                Student.COLUMN_SURNAME},
                new int[] {R.id.tv_student_id, R.id.tv_student_Meno, R.id.tv_student_Priezvisko},
                0);
        ListView lv = (ListView) findViewById(R.id.main_listView);
        lv.setAdapter(myAdapter);
    }

    private void pridejListener(){
        ListView lv = (ListView) findViewById(R.id.main_listView);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, PredmetyActivity.class);
            intent.putExtra("idStudent", id);
            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_Pridaj) {
            startActivityForResult(new Intent(this, PridajStudenta.class), 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume(){
        super.onResume();
        pripojAdapter();
    }

    private void pridajDlhyListener(){
        ListView lv = (ListView) findViewById(R.id.main_listView);
        lv.setOnItemLongClickListener((parent, view, position, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Chcete zmazat studenta?")
                    .setTitle("Mazanie ...")
                    .setPositiveButton("Ano", (dialog, which) -> {
                        dbh.deleteStudent(id);
                        pripojAdapter();
                        Toast.makeText(this, "Student bol zmazany", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Nie", (dialog, which) -> {
                        dialog.dismiss();
                        Toast.makeText(this, "Zmazanie nebolo vykonane", Toast.LENGTH_SHORT).show();
                    })
                    .show();

            return true;
        });
    }

    private void dummyData(){
        int studentov = 5;
        int predmetov = 3;
        int znamok = 3;
        for (int i = 0; i < studentov; i++) {
            dbh.addStudent(new Student("Meno_" + i, "Priezvisko_" + i));
        }
        for (int i = 0; i < predmetov; i++) {
            dbh.addPredmet(new Predmet("Predmet_" + i));
        }
        for (int i = 0; i < studentov; i++) {
            for (int j = 0; j < predmetov; j++) {
                for (int k = 0; k < znamok; k++) {
                    dbh.addZnamka(new Znamka(j + 1, i + 1, (k + 1) ));
                }
            }
        }
    }


}