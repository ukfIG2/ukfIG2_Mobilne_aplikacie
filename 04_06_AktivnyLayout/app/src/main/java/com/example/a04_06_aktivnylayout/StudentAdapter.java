package com.example.a04_06_aktivnylayout;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
        TextView t2;
        TextView t3;
        CheckBox ch;
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
            holder.t1 = (TextView) row.findViewById(R.id.textViewCH_meno);
            holder.t2 = (TextView) row.findViewById(R.id.textViewCH_PR1);
            holder.t3 = (TextView) row.findViewById(R.id.textViewCH_PR2);
            holder.ch = (CheckBox) row.findViewById(R.id.checkBoxCH);
            holder.ch.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Student s = studentList.get((Integer)view.getTag());
                                                 s.Pritomnost = ((CheckBox)view).isChecked();
                                                 Log.d(s.toString(),""+view.getTag());

                                             }
                                         }
            );
            row.setTag(holder); // pre row nastavím odkaz na vytvorený obsah
        } else {
            // ak row nebol null, tak ziskam obsah, ktory bol pren nastaveny
            holder = (ViewHolder) row.getTag();
        }

        Student s = studentList.get(position);
        holder.t1.setText(s.meno);
        holder.t2.setText(s.PR1);
        holder.t3.setText(s.PR2);
        holder.ch.setChecked(s.Pritomnost);
        holder.ch.setTag(position);

        // the view must be returned to our activity
        return row;
    }
}
