package com.example.a06_04_du_xamp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ObjednavatelAdapter extends ArrayAdapter<Objednavatel> {
    public ObjednavatelAdapter(Context context, List<Objednavatel> objednavatelia) {
        super(context, 0, objednavatelia);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Objednavatel objednavatel = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.objednavatel_list, parent, false);
        }
        TextView tvId = convertView.findViewById(R.id.objednavatel_id);
        TextView tvMeno = convertView.findViewById(R.id.objednavatel_meno);
        TextView tvPriezvisko = convertView.findViewById(R.id.objednavatel_priezvisko);
        TextView tvMesto = convertView.findViewById(R.id.objednavatel_mesto);

        tvId.setText(objednavatel.getIdPouzivatel());
        tvMeno.setText(objednavatel.getMeno());
        tvPriezvisko.setText(objednavatel.getPriezvisko());
        tvMesto.setText(objednavatel.getMesto());

        return convertView;
    }
}