package com.example.a05_du_databaza;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


public class PredmetyActivity extends AppCompatActivity {

    Databaza dbh = new Databaza(this);
    SimpleCursorAdapter myAdapter;
    Intent intent;

    Button edite;
    Button Koniec;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_predmety);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        pripojAdapter();
        pripojListener();
        pridajDlhyListener();

        /*Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                System.out.println("Predmety " + key + ": " + extras.get(key));
            }
        }*/

        edite = findViewById(R.id.predmety_bt_Edit);
        edite.setOnClickListener(e -> {
            Intent intentEdit = new Intent(this, EditStudent.class);
            intentEdit.putExtra("idStudent", intent.getLongExtra("idStudent", -1));
            startActivity(intentEdit);
        });

        Koniec = findViewById(R.id.predmety_bt_finish);
        Koniec.setOnClickListener(e -> finish());



    }

    private void pripojAdapter(){
        toolbar = findViewById(R.id.predmety_toolbar);
        toolbar.setTitle(dbh.getStudent(intent.getLongExtra("idStudent", -1)).getName() + " " + dbh.getStudent(intent.getLongExtra("idStudent", -1)).getSurname());
        setSupportActionBar(toolbar);
        myAdapter = new SimpleCursorAdapter(this,
                R.layout.list_llayout_predmety,
                dbh.getPredmety(),
                new String[] {  "_id",
                        Predmet.COLUMN_NAME},
                new int[] {R.id.tv_predmet_id, R.id.tv_premdet_Nazov},
                0);
        ListView lv = (ListView) findViewById(R.id.predmety_listView);
        lv.setAdapter(myAdapter);
    }

   /* private void pripojListener(){
        ListView lv = (ListView) findViewById(R.id.predmety_listView);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            /*if (cursor.moveToFirst()) {
                do {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String columnName = cursor.getColumnName(i);
                        String columnValue = cursor.getString(i);
                        System.out.println("Totok " + columnName + ": " + columnValue);
                    }
                } while (cursor.moveToNext());
            }*/

      /*      @SuppressLint("Range") String idPredmet = cursor.getString(cursor.getColumnIndex("_id"));
            int idStudent = getIntent().getIntExtra("idStudent", -1);

            Intent intentZnamky = new Intent(this, ZnamkyActivity.class);
            intentZnamky.putExtra("idPredmet", idPredmet);
            intentZnamky.putExtra("idUser", getIntent().getLongExtra("idUser", -1));
            startActivity(intentZnamky);
        });
    }*/

   /* private void pripojListener(){
        ListView lv = (ListView) findViewById(R.id.predmety_listView);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            int columnIndex = cursor.getColumnIndex("_id");
            if (columnIndex != -1) {
                String idPredmet = cursor.getString(columnIndex);
                Log.d("MyApp", "idPredmet: " + idPredmet);
                int idStudent = getIntent().getIntExtra("idStudent", -1);

                Intent intentZnamky = new Intent(this, ZnamkyActivity.class);
                intentZnamky.putExtra("idPredmet", idPredmet);
                intentZnamky.putExtra("idStudent", idStudent);
                startActivity(intentZnamky);
            } else {
                System.out.println("MyApp" + "Column " + Predmet.COLUMN_ID + " does not exist in the Cursor");
            }
        });
    }*/

    private void pripojListener(){
        ListView lv = (ListView) findViewById(R.id.predmety_listView);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intentVen = new Intent(this, ZnamkyActivity.class);
            intentVen.putExtra("idPredmet", id);
            intentVen.putExtra("idStudent", intent.getLongExtra("idStudent", -1));
            startActivity(intentVen);
        });
        }

    protected void onResume() {
        super.onResume();
        pripojAdapter();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.predmety_meu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_predmet_pridaj) {
            startActivityForResult(new Intent(this, PridajPredmet.class), 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void pridajDlhyListener(){
        ListView lv = (ListView) findViewById(R.id.predmety_listView);
        lv.setOnItemLongClickListener((parent, view, position, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vymazanie predmetu,")
                    .setMessage("Naozaj chcete vymazať tento predmet?")
                    .setPositiveButton("Áno", (dialog, which) -> {
                        dbh.deletePredmet(id);
                        pripojAdapter();
                        Toast.makeText(this, "Predmet bol vymazaný", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Nie", (dialog, which) -> {
                        dialog.dismiss();
                        Toast.makeText(this, "Vymazanie nebolo vykonané", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            return true;
        });
    }*/
    private void pridajDlhyListener(){
        ListView lv = (ListView) findViewById(R.id.predmety_listView);
        lv.setOnItemLongClickListener((parent, view, position, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Options for predmet")
                    .setMessage("Please select an option")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbh.deletePredmet(id);
                        pripojAdapter();
                        Toast.makeText(this, "Predmet bol vymazaný", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Update", (dialog, which) -> {
                        Intent intentEdit = new Intent(this, EditPredmet.class);
                        intentEdit.putExtra("idPredmet", id);
                        intentEdit.putExtra("predmetName", dbh.getPredmetName(id));
                        startActivity(intentEdit);
                    })
                    .setNeutralButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                        Toast.makeText(this, "Operation was cancelled", Toast.LENGTH_SHORT).show();
                    })
                    .show();
            return true;
        });
    }


}

