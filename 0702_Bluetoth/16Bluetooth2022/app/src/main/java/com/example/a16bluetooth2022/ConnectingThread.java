package com.example.a16bluetooth2022;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.UUID;

class ConnectingThread extends Thread {
    private BluetoothSocket bluetoothSocket;
    // private final BluetoothDevice device2;
    BluetoothAdapter BA;
    ConnectedThread comThread;
    Handler mHandler;
    Context context;

    public ConnectingThread(BluetoothAdapter iBA, BluetoothDevice dev2, UUID uuid, ConnectedThread ci, Handler ih, Context ic) {
        BluetoothSocket temp = null;
        BA = iBA;
        comThread = ci;
        mHandler = ih;
        context = ic;

        checkAllPermissions();
        if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            bluetoothSocket = null;
            return;
        }
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            temp = dev2.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            temp = null;
        }
        bluetoothSocket = temp;
    }

    public void run() {
        // Cancel any discovery as it will slow down the connection
        checkAllPermissions();
        if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        BA.cancelDiscovery();
        try {
            // This will block until it succeeds in connecting to the device
            // through the bluetoothSocket or throws an exception
            bluetoothSocket.connect();
        } catch (IOException connectException) {
            connectException.printStackTrace();
            try {
                bluetoothSocket.close();
            } catch (IOException closeException) {   closeException.printStackTrace(); }
        }
        comThread.setSocket(bluetoothSocket);
        try {
            // Send the obtained bytes to the UI Activity
            mHandler.obtainMessage(CommunActivity.ACCEPTED, -1, -1, null)
                    .sendToTarget();
        } catch (Exception e) {
            Log.e("", "error", e);
        }
        comThread.start();
    }

    // Cancel an open connection and terminate the thread
    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAllPermissions() {
        int version = android.os.Build.VERSION.SDK_INT;
        if (version < 26) { // vsetko je nastavene v manifeste, netreba nic riesit
            //    <uses-permission android:name="android.permission.BLUETOOTH" />
            //    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
        }
        if (version == 29) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        929);
            }
        }
        if (version == 31) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        931);
            }
        }

    }


}