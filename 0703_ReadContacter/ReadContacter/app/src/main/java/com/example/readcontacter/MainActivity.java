package com.example.readcontacter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvZoznam;
    ArrayList<String> contactList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvZoznam = (ListView) findViewById(R.id.listView);
        contactList = new ArrayList<String>();

        Button b = findViewById(R.id.button);
        b.setOnClickListener((view) -> buttonClick(view));

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        }

        priprava2();
    }

    private void priprava2() {
        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener((view) -> ulozClick(view));

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CONTACTS},
                    2);
        }
    }


    public void buttonClick(View view) {
        Citac c = new Citac(); // potomok AsyncTask-u
        c.execute();
    }

    private class Citac extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getContacts(); // napln zoznam kontaktami
            return null;   // return je tu povinny
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // ak je vytvoreny zoznam neprazdny
            if (contactList != null && contactList.size() > 0) {
                // zobraz v bare pocet kontaktov
                getSupportActionBar().setSubtitle(contactList.size() + " kontaktov");
                // vytvor a pripoj adapter
                adapter = new ArrayAdapter(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        contactList);
                lvZoznam.setAdapter(adapter);
            }
            Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
        }

        private void getContacts() {
            Cursor cursor; // kurzor na prechod po db.
            // ziskame jednoznacny ukazovatel na kontakty
            Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
            // ziskame resolver
            ContentResolver contentResolver = getContentResolver();
            // pouzijeme ho na pristup k danej URI – vyberieme vsetky data v raw zoradeni
            cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

            // prechod po vsetkych kontaktoch
            if (cursor.getCount() > 0) {
                int id_index = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int id_display_name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                // existencia telefonnych cisel daneho kontaktu
                int id_hpn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                // nazvy stlpcov pre udaje DATA1-15
                Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

                while (cursor.moveToNext()) {
                        String contact_id = cursor.getString(id_index);
                        String name = cursor.getString(id_display_name);
                        // vyskladame vsetky udaje o kontakte pridanim aj dalsich parametrov
                        String kontakt = name;
                        // zistime kolko ma telefonnych cisel
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(id_hpn));

                        if (hasPhoneNumber == 1) { // ak ten kontakt ma nejake tel. cisla
                            // prejdeme po zozname telefonov daneho kontaktu – nastavime filter
                            Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null,
                                    Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                            int id_number = phoneCursor.getColumnIndex(NUMBER);
                            while (phoneCursor.moveToNext()) { // pridame tel. do retazca kontakt
                                kontakt += "\n - " +
                                        phoneCursor.getString(id_number);
                            }
                            phoneCursor.close(); // zavrieme kurzor na prechod pozozname telefonov
                        }
                        // pridame kontakt s udajmi do zoznamu
                        contactList.add(kontakt);
                } // koniec while
            } // koniec if-u
            cursor.close(); // zatvorenie hlavneho cursor-a

        }
    }

        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && // skumam 0. opravnenie
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Kontakty - OK", Toast.LENGTH_SHORT).show();
                } else {
                    // button.setEnabled(false); // deaktivujem tlacidlo, textové pole..
                    Toast.makeText(this, "Kontakty nebudu", Toast.LENGTH_SHORT).show();
                }
                case 2:
                if (grantResults.length > 0 && // skumam 0. opravnenie
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Kontakty zapis - OK", Toast.LENGTH_SHORT).show();
                } else {
                    // button.setEnabled(false); // deaktivujem tlacidlo, textové pole..
                    Toast.makeText(this, "Kontakty zapis nebudu", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void ulozClick(View view) {
        EditText et1 = findViewById(R.id.editText1);
        EditText et2 = findViewById(R.id.editText1);

        pridajKontakt (et1.getText().toString(), et2.getText().toString());
    }

    private void pridajKontakt(String meno, String telefon) {
        String DisplayName = meno;
        String MobNr = telefon;
        String HomeNr = "1111";

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        DisplayName)
                .build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobNr)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNr)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}