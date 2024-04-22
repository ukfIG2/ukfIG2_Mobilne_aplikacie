package com.example.a16bluetooth2022;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommunActivity extends AppCompatActivity {
    public static final int CONNECTED = 0;
    public static final int ACCEPTED = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;

    BluetoothDevice device2 = null;
    BluetoothAdapter BA;

    ListeningThread lt = null;
    ConnectingThread ct = null;
    ConnectedThread conThread = null;

    ListView lv;
    private ArrayAdapter<String> adapter;

    ActivityResultLauncher<Intent> selectedDeviceActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bundle vrateneData = result.getData().getExtras();
                    String MAC = vrateneData.getString("device");
                    device2 = BA.getRemoteDevice(MAC);
                    // Initiate a connection request in a separate thread
                    if (ct != null) ct.cancel();
                    ct = new ConnectingThread(BluetoothAdapter.getDefaultAdapter(),device2, MainActivity.MY_UUID, conThread, mHandler, CommunActivity.this);
                    ct.start();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commun);
        setActions();
        BA = BluetoothAdapter.getDefaultAdapter();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv = (ListView) findViewById(R.id.listViewCOM);
        lv.setAdapter(adapter);
        if (conThread != null) conThread.cancel();
        conThread = new ConnectedThread(mHandler);
    }

    private void setActions() {
        Button b1 = (Button) findViewById(R.id.button10);
        b1.setOnClickListener(view -> serverClick());
        Button b2 = (Button) findViewById(R.id.button11);
        b2.setOnClickListener(view -> pripojMaClick());
        Button b3 = (Button) findViewById(R.id.button12);
        b3.setOnClickListener(view -> odosliMsgClick());
    }

    public void serverClick() {
        if (lt != null) lt.cancel();
        lt = new ListeningThread(BluetoothAdapter.getDefaultAdapter(),MainActivity.MY_UUID, conThread, mHandler, this);
        lt.start();
    }

    public void pripojMaClick() {
        Intent z = new Intent(this, PairedDevicesActivity.class);
        selectedDeviceActivityResultLauncher.launch(z);
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            TextView tv = (TextView) findViewById(R.id.textView);
            switch (msg.what) {
                case CONNECTED:
                    adapter.add("Pripojeny:  ");
                    break;
                case ACCEPTED:
                    adapter.add("Akceptovany:  ");
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    adapter.add("JA:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    adapter.add(readMessage);
                    break;
            }
        }
    };

    public void odosliMsgClick() {
        EditText ed = (EditText) findViewById(R.id.editText);
        String s = ed.getText().toString();
        ed.setText("");
        byte[] writeBuf = s.getBytes();
        if (conThread == null)
            Toast.makeText(this, "neni vlakno", Toast.LENGTH_SHORT).show();
        else
            conThread.write(writeBuf);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lt != null) lt.cancel();
        if (ct != null) ct.cancel();
        if (conThread != null) conThread.cancel();
    }
}