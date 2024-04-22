package com.example.a16bluetooth2022;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final UUID MY_UUID = UUID.fromString("00112233-afac-1234-abcd-abcdef012345");
    private BluetoothAdapter BA;
    ActivityResultLauncher<Intent> bluetoothTurnActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Toast.makeText(MainActivity.this, "zapnute", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "zrusene", Toast.LENGTH_SHORT).show();
                    }
                    zobrazBTStatus();
                }
            });

    public static int getAndroidAPIversion() {
        return android.os.Build.VERSION.SDK_INT;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zobrazBTStatus();
        setActions();
        checkAllPermissions();
    }

    private void checkAllPermissions() {
        int version = android.os.Build.VERSION.SDK_INT;
        if (version < 26) { // vsetko je nastavene v manifeste, netreba nic riesit
            //    <uses-permission android:name="android.permission.BLUETOOTH" />
            //    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
        }
        if (version == 29) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        929);
            }
        }
        if (version == 31) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        931);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 929:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "ACCESS_COARSE_LOCATION - OK", Toast.LENGTH_SHORT).show();
                } else {
                    vypniButtony();
                }
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "ACCESS_FINE_LOCATION - OK", Toast.LENGTH_SHORT).show();
                } else {
                    vypniButtony();
                }
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "ACCESS_BACKGROUND_LOCATION - OK", Toast.LENGTH_SHORT).show();
                } else {
                    vypniButtony();
                }

        }
    }

    private void vypniButtony() {
        ((Button) findViewById(R.id.button6)).setEnabled(false);
        ((Button) findViewById(R.id.button7)).setEnabled(false);
        ((Button) findViewById(R.id.button)).setEnabled(false);
        ((Button) findViewById(R.id.button3)).setEnabled(false);
    }

    private void setActions() {
        Button b1 = (Button) findViewById(R.id.button6);
        b1.setOnClickListener(view -> zviditelniSa());
        Button b2 = (Button) findViewById(R.id.button7);
        b2.setOnClickListener(view -> skenuj(view));
        Button b3 = (Button) findViewById(R.id.button);
        b3.setOnClickListener(view -> zoznamSparovanych(view));
        Button b4 = (Button) findViewById(R.id.button3);
        b4.setOnClickListener(view -> chatuj(view));
    }

    private void zviditelniSa() {
        checkAllPermissions();
        if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        BA.startDiscovery();
        Intent disIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        disIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        Toast.makeText(this, "good day", Toast.LENGTH_SHORT).show();
        startActivity(disIntent);
    }

    private void zobrazBTStatus () {
        BA = BluetoothAdapter.getDefaultAdapter();
        TextView tv = (TextView) findViewById(R.id.textView4);
        if (BA == null) {
            tv.setText("adaptér nie je");
        } else {
            if (BA.isEnabled())
                tv.setText("zapnutý " + getAndroidAPIversion());
            else
                tv.setText("vypnutý " + getAndroidAPIversion());
        }
    }

    public void zapniClick (View view){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            bluetoothTurnActivityResultLauncher.launch(turnOn);
            Toast.makeText(getApplicationContext(), "Pokus o zapnutie", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Už je zapnuté", Toast.LENGTH_LONG).show();
        }
    }

    public void zoznamSparovanych (View view){
        Intent z = new Intent(MainActivity.this, PairedDevicesActivity.class);
        startActivity(z);
    }

    public void skenuj (View v){
        Intent in = new Intent(MainActivity.this, SkenActivity.class);
        Toast.makeText(this, "idem", Toast.LENGTH_SHORT).show();
        startActivity(in);
    }

    public void chatuj (View view){
        Intent in = new Intent(MainActivity.this, CommunActivity.class);
        startActivity(in);
    }
}

