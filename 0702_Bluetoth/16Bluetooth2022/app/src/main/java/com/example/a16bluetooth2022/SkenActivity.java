package com.example.a16bluetooth2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class SkenActivity extends AppCompatActivity {
    private BluetoothAdapter BA;
    ArrayList<String> zoznam;
    ArrayList<BluetoothDevice> zoznamDev;
    ArrayAdapter adapter;
    ListView lv;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.d("xxxxx","action");
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(context, "Začínam hľadať ... ", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context, "... Koniec vyhľadávania", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                addDevice(intent);
            }
        }

        private void addDevice(Intent intent) {
            if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(SkenActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Toast.makeText(getApplicationContext(), "Nájdené " + device.getName(), Toast.LENGTH_SHORT).show();
            zoznam.add(device.getName());
            zoznamDev.add(device);
            adapter.notifyDataSetChanged();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sken);

        zoznam = new ArrayList();
        zoznamDev = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, zoznam);

        lv = (ListView) findViewById(R.id.listViewSC);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((adapterView, view, i, l) -> onItemClick(adapterView, view, i, l));

        Button b1 = (Button) findViewById(R.id.buttonSC);
        b1.setOnClickListener(view -> skenujOkolie());
        BA = BluetoothAdapter.getDefaultAdapter();
    }

    private void onItemClick(AdapterView parent, View v, int position, long id) {
        checkAllPermissions();
        if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        BA.cancelDiscovery();
        pairDevice(zoznamDev.get(position));
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Chyba!", Toast.LENGTH_SHORT).show();
        }
    }

    public void skenujOkolie() {
        if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);
        Toast.makeText(this, "starting", Toast.LENGTH_SHORT).show();

        if (BA.isDiscovering()) BA.cancelDiscovery();
        if (!BA.startDiscovery()) Log.d("xxxx","discovery error");
    }

    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 929:
                if (android.os.Build.VERSION.SDK_INT == 29) {
                    if (grantResults.length == 3 && (grantResults[0] != PackageManager.PERMISSION_GRANTED
                            || grantResults[1] != PackageManager.PERMISSION_GRANTED
                            || grantResults[2] != PackageManager.PERMISSION_GRANTED))
//                            || grantResults[3] != PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Malo opravneni", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            case 931: { //...
                       }
        }
    }

}