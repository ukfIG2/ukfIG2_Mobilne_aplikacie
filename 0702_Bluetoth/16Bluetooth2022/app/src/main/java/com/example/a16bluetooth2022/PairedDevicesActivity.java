package com.example.a16bluetooth2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class PairedDevicesActivity extends AppCompatActivity {
    ArrayAdapter adapter;
    ArrayList<String> zoznam;
    private Set<BluetoothDevice> pairedDevices;
    BluetoothAdapter BA;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);
        lv = (ListView) findViewById(R.id.listViewPD);

        BA = BluetoothAdapter.getDefaultAdapter();
        UkazZoznam();
    }

    private void UkazZoznam() {
        zoznam = new ArrayList();
        if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        pairedDevices = BA.getBondedDevices();
        for (BluetoothDevice bt : pairedDevices) {
            zoznam.add(bt.getAddress() + "(" + bt.getName() + ")");
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, zoznam);
        lv.setOnItemClickListener((adapterView, view, i, l) -> onItemClick(adapterView, view, i, l));
        lv.setAdapter(adapter);
    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent intentNavrat = new Intent();
        intentNavrat.putExtra("device", zoznam.get(position).substring(0, 17));
        setResult(RESULT_OK, intentNavrat);
        finish();
    }

    private void checkAllPermissions() {
        int version = android.os.Build.VERSION.SDK_INT;
        if (version < 26) { // vsetko je nastavene v manifeste, netreba nic riesit
            //    <uses-permission android:name="android.permission.BLUETOOTH" />
            //    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
        }
        if (version == 29) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions (this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        //       Manifest.permission.BLUETOOTH_SCAN},
                        929);
            }
        }
        // version 31 - BLUETOOTH_SCAN
        if (version == 31) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions (this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.BLUETOOTH_SCAN},
                        931);
            }
        }
    }
}