package com.example.a16bluetooth2022;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.UUID;

public class ListeningThread extends Thread {
    private BluetoothServerSocket bluetoothServerSocket;
    ConnectedThread comThread;
    Handler mHandler;
    Context context;

    public ListeningThread(BluetoothAdapter BA, UUID uuid, ConnectedThread ci, Handler ih, Context ic) {
        BluetoothServerSocket temp = null;
        comThread = ci;
        mHandler = ih;
        context = ic;

        checkAllPermissions();
        try {
            if (android.os.Build.VERSION.SDK_INT == 31 && ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                bluetoothServerSocket = temp;
                return;
            }
            temp = BA.listenUsingRfcommWithServiceRecord("MyChatApp", uuid);
        } catch (IOException e) {
            temp = null;
        }
        bluetoothServerSocket = temp;
    }

    public void run() {
        BluetoothSocket bluetoothSocket;
        // This will block while listening until a BluetoothSocket is returned
        // or an exception occurs
        while (true) {
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
            } catch (IOException e) { break; }

            // If a connection is accepted
            if (bluetoothSocket != null) {
                // Manage the connection in a separate thread
                comThread.setSocket(bluetoothSocket);
                    // Send the obtained bytes to the UI Activity
                mHandler.obtainMessage(CommunActivity.CONNECTED, -1, -1, null)
                            .sendToTarget();
                comThread.start();

                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    // Cancel the listening socket and terminate the thread
    public void cancel() {
        try {
            bluetoothServerSocket.close();
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