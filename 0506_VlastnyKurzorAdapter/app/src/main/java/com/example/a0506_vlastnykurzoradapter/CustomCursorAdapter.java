package com.example.a0506_vlastnykurzoradapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Sinus on 21.03.2017.
 */

public class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor cursor, int flags){
        // len zavoláme konštruktor predka, nie je potrebné nič meniť
        super(context, cursor, flags);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
    // vráti/nastaví inflater, ktorý bude napĺňať podľa daného xml
    // s popisom prvkov pre položku list-u

        return LayoutInflater.from
                (context).inflate(R.layout.list_layout, // ako naplnat
                parent, // čo naplnať
                false); // či údaje majú byť pridané
        // do parametrov rootu, false len
        // vytvorí položku Viewu a nič nerieši
    }


    public void bindView(View view, Context context, Cursor cursor) {
        // sprístupníme si zdroje, do ktorých budeme vladať údaje
        TextView tvid = (TextView) view.findViewById(R.id.id1);
        TextView tvmeno = (TextView) view.findViewById(R.id.name1);
        TextView tvemail = (TextView) view.findViewById(R.id.email1);
        // pripravíme si údaje z db prostredníctvom cursora
        // slušnosť káže používať konštanty, nepoužívame ich kvôli prehľadnosti
        String id = cursor.getString(cursor.getColumnIndex(MyContract.Student.COLUMN_ID));
        String meno = cursor.getString(cursor.getColumnIndex(MyContract.Student.COLUMN_NAME));
        String email = cursor.getString(cursor.getColumnIndex(MyContract.Student.COLUMN_EMAIL));
        // podľa pohlavia zafarbíme – s resourcami tu môžeme robiť všetko
        if (meno.substring(meno.length()-1).equals("a")){
            tvmeno.setTextColor(Color.RED);  // farba textu
        } else { tvmeno.setBackgroundColor(Color.GREEN); } // farba pozadia
        // data z db nastavíme do textView-ov položky listu
        tvid.setText(id);
        tvmeno.setText(meno + " " + cursor.getPosition());
        tvemail.setText(email);
    }

}
