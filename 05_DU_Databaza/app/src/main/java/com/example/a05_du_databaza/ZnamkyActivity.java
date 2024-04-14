package com.example.a05_du_databaza;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ZnamkyActivity extends AppCompatActivity {

    private Databaza dbh = new Databaza(this);
    private SimpleCursorAdapter myAdapter;
    private Button koniec;
    private Intent intent;
    private Toolbar tb;
    private long idStudent;
    private long idPredmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_znamky);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        idStudent = intent.getLongExtra("idStudent", -1);
        idPredmet = intent.getLongExtra("idPredmet", -1);
        //System.out.println("ID Studenta: " + idStudent);
        //System.out.println("ID Predmetu: " + idPredmet);

        pripojAdapter();
        pridajListener();

        koniec = findViewById(R.id.bt_znamky_finish);
        koniec.setOnClickListener(e -> finish());

        tb = findViewById(R.id.znamky_toolbar);
        nacitanieDat();


        Bundle extras = getIntent().getExtras();
        /*if (extras != null) {
            for (String key : extras.keySet()) {
                System.out.println("Znamky " + key + ": " + extras.get(key));
            }
        }*/

        Toolbar tb = findViewById(R.id.znamky_toolbar);
        setSupportActionBar(tb);

    }

    private void pripojAdapter(){

        /*Cursor cursor = dbh.getZnamky(idStudent, idPredmet);
        if(cursor.moveToFirst()){
            do{
                System.out.println("Znamka: " + cursor.getString(cursor.getColumnIndexOrThrow(Znamka.COLUMN_ZNAMKA)));
                System.out.println("ID: " + cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                System.out.println("Nieco");
            }while(cursor.moveToNext());
        }System.out.println("Koniec");*/

       myAdapter = new SimpleCursorAdapter(this,
               R.layout.list_layout_znamky,
               dbh.getZnamky(idStudent, idPredmet),
               new String[]{"_id",
                            Znamka.COLUMN_ZNAMKA},
               new int[]{R.id.tv_znamky_id, R.id.tv_znamky_hodnoty},
               0);
        ListView lv = (ListView) findViewById(R.id.znamky_listView);
        lv.setAdapter(myAdapter);
    }

    private void pridajListener(){
        ListView lv = (ListView) findViewById(R.id.znamky_listView);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            long idZnamky = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            Intent intent = new Intent(this, Select_Znamka.class);
            intent.putExtra("idZnamky", idZnamky);
            startActivity(intent);
        });
    }



    protected void onResume() {
        super.onResume();
        pripojAdapter();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.znamky_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.znamky_optM_Pridaj) {
            Intent intent = new Intent(this, PridajZnamku.class);
            intent.putExtra("idStudent", idStudent);
            intent.putExtra("idPredmet", idPredmet);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nacitanieDat(){
        tb.setTitle(dbh.getStudent(idStudent).getName() + " " + dbh.getStudent(idStudent).getSurname() + " " + dbh.getPredmet(idPredmet).getName());
    }



}