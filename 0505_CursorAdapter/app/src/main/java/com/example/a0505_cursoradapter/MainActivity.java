package com.example.a0505_cursoradapter;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SimpleCursorAdapter myAdapter;
    // získam inštanciu, ktorá má prístup k databáze
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //VlozStudentov();
        PripojAdapter();
    }

    private void PripojAdapter() {
        myAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_layout, // definovany layout
                dbh.VratKurzor(), 	 // kurzor = ukazovateľ na udaje o studentoch
                // = na dotaz
                new String[] { MyContract.Student.COLUMN_ID,MyContract.Student.COLUMN_NAME,MyContract.Student.COLUMN_EMAIL,MyContract.Student.COLUMN_AGE}, // zoznam from - názvy
                // polí z dotazu
                new int[] {R.id.id1, R.id.name1, R.id.email1, R.id.age1}, // ciele
                // zobrazovania
                0); // flag definuje spravanie adaptera, 0 je ok 

        ListView lv= (ListView)findViewById(R.id.listView);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ziskam odkaz na moj listview
                ListView lv= (ListView)findViewById(R.id.listView);
                // z lv dostanem odkaz na jeho cursor
                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                // position je parameter z udalosti on&long&cick
                cursor.moveToPosition(position);
                // ziskam  id
                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Student.COLUMN_ID));
                Toast.makeText(MainActivity.this, ""+ID, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // vytvoríme buildera, ktorý nám poskladá dialóg
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());

                // vyskladáme dialóg nastavením parametrov
                builder.setMessage("Vymazať naozaj?");
                builder.setTitle("Mazanie ...")
                       .setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ziskam odkaz na moj listview
                                ListView lv= (ListView)findViewById(R.id.listView);
                                // z lv dostanem odkaz na jeho cursor
                                Cursor cursor = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                                // position je parameter z udalosti on&long&cick
                                cursor.moveToPosition(position);
                                // ziskam  id
                                long ID = cursor.getLong(cursor.getColumnIndex(MyContract.Student.COLUMN_ID));
                                // a uz len zavolam mazanie
                                dialog.dismiss(); // zavri

                                Toast.makeText(MainActivity.this, "mazem", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // zavri
                                Toast.makeText(MainActivity.this, "nic sa nedeje", Toast.LENGTH_SHORT).show();
                            }
                        });
                // vyvoláme zobrazenie nadefinovaného dialógu
                builder.show();
                return false;
            }});
      }


    private void VlozStudentov() {
        dbh.addStudent(new Student(1, "miso", "mm", 15));
        dbh.addStudent(new Student(3, "miska", "mmi", 21));
    }



}
