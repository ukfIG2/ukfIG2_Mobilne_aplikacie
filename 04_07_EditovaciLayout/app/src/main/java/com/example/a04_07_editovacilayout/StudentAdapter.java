package com.example.a04_07_editovacilayout;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    private ArrayList<Student> studentList;
    private Context context;
    private int layoutResourceId;

    /* here we must override the constructor for ArrayAdapter
     * the only variable we care about now is ArrayList<Item> objects,
     * because it is the list of objects we want to display.*/
    public StudentAdapter(Context context, int textViewResourceId, ArrayList<Student> objects) {
        super(context, textViewResourceId, objects);
        this.studentList = objects;
        this.context = context;
        layoutResourceId = textViewResourceId;
    }

    class ViewHolder {
        TextView t1;
        EditText t2;
        EditText t3;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;   // naplnany riadok
        ViewHolder holder = null; // reprezentant obsahu riadku

        if (row == null) {
            // ziskam inflater
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            // cez inflater sa dostanem k štruktúre riadku
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder(); // row ešte nemá komponenty, je null => vytvorim
            // a napojím sa na ne
            holder.t1 = (TextView) row.findViewById(R.id.textView);
            holder.t2 = (EditText) row.findViewById(R.id.editPR1);
            holder.t3 = (EditText) row.findViewById(R.id.editPR2);

            // we need to update adapter once we finish with editing
            holder.t2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) { // ak sa zmenil focus a už ho nemá, t.j. odišlo sa
                        int p = (Integer)v.getTag(); // parameter z hlavičky onView
                        EditText Caption = (EditText) v; // parameter z onFocusChange
                        // nastavím v datach (StudentList) p-tej položke hodnotu z Editu
                        studentList.get(p).PR1 = Caption.getText().toString();
                        Log.d("v",studentList.get(p).toString());
                    }
                }
            });

            // we need to update adapter once we finish with editing
            holder.t3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) { // ak sa zmenil focus a už ho nemá, t.j. odišlo sa
                        int p = (Integer)v.getTag(); // parameter z hlavičky onView
                        EditText Caption = (EditText) v; // parameter z onFocusChange
                        // nastavím v datach (StudentList) p-tej položke hodnotu z Editu
                        studentList.get(p).PR2 = Caption.getText().toString();
                        Log.d("v",studentList.get(p).toString());
                    }
                }
            });

            row.setTag(holder); // pre row nastavím odkaz na vytvorený obsah
        } else {
            // ak row nebol null, tak ziskam obsah, ktory bol pren nastaveny
            holder = (ViewHolder) row.getTag();
        }

        Student s = studentList.get(position);
        holder.t1.setText(s.meno);
        holder.t2.setText(s.PR1);
        holder.t2.setTag(position);
        holder.t3.setText(s.PR2);
        holder.t3.setTag(position);

        // the view must be returned to our activity
        return row;
    }
}
