package com.example.notificationlistener3;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothSerialService extends Service {
    private static String tag = "IISP BLService";
//    public static final String address = "B4:E6:2D:C0:B8:4B";
    public static final String address = "B4:E6:2D:D3:63:87";
    public static String BLUETOOTH_FAILED = "bluetooth-connection-failed";
    public static String BLUETOOTH_CONNECTED = "bluetooth-connection-started";
    public static String BLUETOOTH_DISCONNECTED = "bluetooth-connection-lost";
    AsyncTask<Void, Void, BluetoothDevice> connectionTask;
    BluetoothSerialService.SerialReader serialReader;
    SharedPreferences mSharedPreferences;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    OutputStream serialOutputStream;
    InputStream serialInputStream;
    BluetoothSocket serialSocket;
    boolean connected = false;
    int[] last_value = new int[3];
    private MsgReceiver msgReceiver;

    public BluetoothSerialService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "started service BluetoothService");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
        }
        //listen for bluetooth disconnect
        IntentFilter disconnectIntent = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        BluetoothSerialService.this.registerReceiver(bluetoothReceiver, disconnectIntent);
        //reestablishes a connection is one doesn't exist
        if (!connected) {
            connect();
            Log.i(tag, "Trying to connect");
        } else {
            Intent intent = new Intent(BLUETOOTH_CONNECTED);
            LocalBroadcastManager.getInstance(BluetoothSerialService.this).sendBroadcast(intent);
        }
        msgReceiver = new MsgReceiver();
        IntentFilter messager = new IntentFilter("com.example.notificationblocker.BluetoothSerialService.MESSAGE");
        registerReceiver(msgReceiver, messager);
        mSharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        String lampRBrightness = mSharedPreferences.getString(Util_String.LAMP_R_BRIGHTNESS_STUDY, null);
        String lampGBrightness = mSharedPreferences.getString(Util_String.LAMP_G_BRIGHTNESS_STUDY, null);
        String lampBBrightness = mSharedPreferences.getString(Util_String.LAMP_B_BRIGHTNESS_STUDY, null);
        last_value[0] = (lampRBrightness != null) ? Integer.parseInt(lampRBrightness) : 50;
        last_value[1] = (lampGBrightness != null) ? Integer.parseInt(lampGBrightness) : 50;
        last_value[2] = (lampBBrightness != null) ? Integer.parseInt(lampBBrightness) : 50;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int read(int bufferSize, byte[] buffer) {
        if (bufferSize > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bufferSize; i++) {
                sb.append((char) buffer[i]);
            }
            String message = sb.toString();
            if (message.equals("hN\n")) {
                setRGB(last_value);
            } else if (message.equals("hP0\n")) {
                // disconnected
                Intent exitFocusMode = new Intent(BluetoothSerialService.this, MainActivity.class);
                exitFocusMode.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exitFocusMode);
            } else if (message.equals("hP1\n")) {
                // connected
                setRGB(last_value);
                Intent enterFocusMode = new Intent(BluetoothSerialService.this, TimerActivity.class);
                enterFocusMode.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(enterFocusMode);
            }
            if (buffer[bufferSize - 1] == 10) {
                Log.i(tag, "received message:" + message);
                return bufferSize;
            }
        }
        return 0;
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        if (connected)
            return serialInputStream.read(buffer, byteOffset, byteCount);
        throw new RuntimeException("Connection lost, reconnecting now.");
    }

    public void setRGB(int[] rgb) {
        setRGB(rgb[0], rgb[1], rgb[2]);
    }

    public void setRGB(int r, int g, int b) {
        r = r % 256;
        g = g % 256;
        b = b % 256;
        last_value = new int[]{r, g, b};
        int sum = r + g + b;
        r = r % 256;
        String rs = "" + r;
        String gs = "" + g;
        String bs = "" + b;
        String ss = "" + sum;
        while (("" + rs).length() < 3) {
            rs = "0" + rs;
        }
        while (("" + gs).length() < 3) {
            gs = "0" + gs;
        }
        while (("" + bs).length() < 3) {
            bs = "0" + bs;
        }
        while (("" + ss).length() < 3) {
            ss = "0" + ss;
        }
        String message = "HR" + rs + "G" + gs + "B" + bs + "S" + ss;
        this.write(message);
        Log.i(tag, "Sent message:"+message);
    }

    public void write(String message) {
        message = message + "\n";
        byte[] buffer = message.getBytes();
        if (connected) {
            try {
                serialOutputStream.write(buffer);
            } catch (IOException e) {
                Log.i(tag, "Failed to send message");
            }
        }
    }

    /**
     * Initializes the bluetooth serial connections, uses the LocalBroadcastManager when
     * connection is established
     */
    @SuppressLint("StaticFieldLeak")
    public void connect() {
        if (connected) {
            Log.e(tag, "Connection request while already connected");
            return;
        }
        if (connectionTask != null && connectionTask.getStatus() == AsyncTask.Status.RUNNING) {
            Log.e(tag, "Connection request while attempting connection");
            return;
        }
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            return;
        }
        //AsyncTask to handle the establishing of a bluetooth connection
        connectionTask = new AsyncTask<Void, Void, BluetoothDevice>() {
            int MAX_ATTEMPTS = 30;
            int attemptCounter = 0;

            @Override
            protected BluetoothDevice doInBackground(Void... params) {
                while (!isCancelled()) { //need to kill without calling onCancel
                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                    Log.i(tag, attemptCounter + ": Attempting connection to " + device.getName());
                    try {
                        try {
                            //Standard SerialPortService ID
                            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                            serialSocket = device.createRfcommSocketToServiceRecord(uuid);
                        } catch (Exception ce) {
                            serialSocket = connectViaReflection(device);
                        }
                        //setup the connect streams
                        serialSocket.connect();
                        serialInputStream = serialSocket.getInputStream();
                        serialOutputStream = serialSocket.getOutputStream();
                        connected = true;
                        Log.i(tag, "Connected to " + device.getName());
                        return device;
                    } catch (Exception e) {
                        serialSocket = null;
                        serialInputStream = null;
                        serialOutputStream = null;
                        Log.i(tag, e.getMessage());
                    }
                    try {
                        attemptCounter++;
                        if (attemptCounter > MAX_ATTEMPTS)
                            this.cancel(false);
                        else
                            Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                Log.i(tag, "Stopping connection attempts");
                Intent intent = new Intent(BLUETOOTH_FAILED);
                LocalBroadcastManager.getInstance(BluetoothSerialService.this).sendBroadcast(intent);
                return null;
            }

            @Override
            protected void onPostExecute(BluetoothDevice result) {
                super.onPostExecute(result);
                bluetoothDevice = result;
                //start thread responsible for reading from inputstream
                serialReader = new BluetoothSerialService.SerialReader();
                serialReader.start();
                //send connection message
                Intent intent = new Intent(BLUETOOTH_CONNECTED);
                LocalBroadcastManager.getInstance(BluetoothSerialService.this).sendBroadcast(intent);
            }
        };
        connectionTask.execute();
    }

    //see: http://stackoverflow.com/questions/3397071/service-discovery-failed-exception-using-bluetooth-on-android
    private BluetoothSocket connectViaReflection(BluetoothDevice device) throws Exception {
        Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
        return (BluetoothSocket) m.invoke(device, 1);
    }

    public int available() throws IOException {
        if (connected)
            return serialInputStream.available();
        throw new RuntimeException("Connection lost, reconnecting now.");
    }

    private class SerialReader extends Thread {
        private static final int MAX_BYTES = 125;
        byte[] buffer = new byte[MAX_BYTES];
        int bufferSize = 0;

        public void run() {
            Log.i("serialReader", "Starting serial loop");
            while (!isInterrupted()) {
                try {
                    //check for some bytes, or still bytes still left in buffer
                    if (available() > 0) {
                        int newBytes = read(buffer, bufferSize, MAX_BYTES - bufferSize);
                        if (newBytes > 0) {
                            bufferSize += newBytes;
                        }
//                        Log.d(tag, "read " + newBytes);
                    }
                    if (bufferSize > 0) {
                        int read = read(bufferSize, buffer);
                        //shift unread data to start of buffer
                        if (read > 0) {
                            int index = 0;
                            for (int i = read; i < bufferSize; i++) {
                                buffer[index++] = buffer[i];
                            }
                            bufferSize = index;
                        }
                    } else {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ie) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    Log.e(tag, "Error reading serial data", e);
                }
            }
            Log.i(tag, "Shutting serial loop");
        }
    }

    public void close() {
        connected = false;
        if (connectionTask != null) {
            connectionTask.cancel(false);
        }
        if (serialReader != null) {
            serialReader.interrupt();
            try {
                serialReader.join(1000);
            } catch (InterruptedException ie) {
            }
        }
        try {
            serialInputStream.close();
        } catch (Exception e) {
            Log.e(tag, "Failed releasing inputstream connection");
        }
        try {
            serialOutputStream.close();
        } catch (Exception e) {
            Log.e(tag, "Failed releasing outputstream connection");
        }
        try {
            serialSocket.close();
        } catch (Exception e) {
            Log.e(tag, "Failed closing socket");
        }
        Log.i(tag, "Released bluetooth connections");
        this.unregisterReceiver(bluetoothReceiver);
        this.unregisterReceiver(msgReceiver);
    }

    /**
     * Listens for discount message from bluetooth system and restablishing a connection
     */
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice eventDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                if (bluetoothDevice != null && bluetoothDevice.equals(eventDevice)) {
                    Log.i(tag, "Received bluetooth disconnect notice");
                    //clean up any streams
                    close();
                    //reestablish connect
                    connect();
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(BLUETOOTH_DISCONNECTED));
                }
            }
        }
    };

    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get the message from the intent
            int r = intent.getIntExtra("R", 255);
            int g = intent.getIntExtra("G", 255);
            int b = intent.getIntExtra("B", 255);
            setRGB(r, g, b);
        }
    }
}


