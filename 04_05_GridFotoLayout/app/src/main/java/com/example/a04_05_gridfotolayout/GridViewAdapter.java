package com.example.a04_05_gridfotolayout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data)  {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        ViewHolder holder = null;

        if (cell == null) { // ak je riadok prázdny
            // získame plniča
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            // získame predpis podľa ktorého sa má bunka naplniť
            cell = inflater.inflate(layoutResourceId, parent, false);
            // vytvoríme v pamäti priestor údaje novej bunky
            holder = new ViewHolder();
            // nasmerujeme holder na komponenty v danej bunke/riadku
            holder.imageTitle = (TextView) cell.findViewById(R.id.text);
            holder.image = (ImageView) cell.findViewById(R.id.image);
            // pre položku zoznamu/gridu nastavíme spojenie na príslušné hodnoty
            cell.setTag(holder);
        } else {
            // ak už bola bunka vytvorená, len vrátime spojenie na view-y
            holder = (ViewHolder) cell.getTag();
        }
        // v premennej holder su komponenty co ideme naplnat
        // prečítame hodnoty zo zdroja
        ImageItem item = (ImageItem)data.get(position);
        holder.imageTitle.setText(item.getTitle()); //
        holder.image.setImageBitmap(item.getImage());
        // vráti vytvorený riadok
        return cell;
    }
}
