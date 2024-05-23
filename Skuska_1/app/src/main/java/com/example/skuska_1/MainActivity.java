package com.example.skuska_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Databaza_lokalna dl = new Databaza_lokalna(this);

    private SimpleCursorAdapter myAdapter;


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

        dl = new Databaza_lokalna(this);

       /* Button importButton = findViewById(R.id.main_import);
        importButton.setOnClickListener(v -> importujDatabazu());*/

        Toolbar tb = findViewById(R.id.main_tool);
        setSupportActionBar(tb);
        //pridajZaner();

        pripojAdapter();
        pridajListener();
    }

    ;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu_Pridaj) {
            Intent intent = new Intent(this, PridajKnihu.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void pripojAdapter() {


        myAdapter = new SimpleCursorAdapter(this,
                R.layout.list_layout_main,
                dl.getKnihy(),
                new String[]{"nazov", "autor", "rok"},
                new int[]{R.id.main_nazov, R.id.main_autor, R.id.main_rok},
                0);
        ListView lv = findViewById(R.id.main_list);
        lv.setAdapter(myAdapter);
    }

    private void pridajZaner() {
        Databaza_lokalna db = new Databaza_lokalna(this);
        zaner zanerRomantika = new zaner("lebo");
        db.addZaner(zanerRomantika);
    }

    private void pridajListener() {
        ListView lv = findViewById(R.id.main_list);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, UpravKnihu.class);
            System.out.println("id: " + id);
            intent.putExtra("id", id);
            startActivity(intent);
        });

    }


    protected void onResume() {
        super.onResume();
        pripojAdapter();
    }
}

   /* private void importujDatabazu() {
        System.out.println("Importujem databazu");
*/
