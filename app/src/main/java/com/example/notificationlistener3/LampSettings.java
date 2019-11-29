package com.example.notificationlistener3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.content.Intent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class LampSettings extends AppCompatActivity {
    Button getDefaultValue;
    Button confirm;
    SeekBar brightnessR;
    SeekBar brightnessG;
    SeekBar brightnessB;
    TextView brightnessRValue;
    TextView brightnessGValue;
    TextView brightnesBValue;
    String SetRBright;
    String SetGBright;
    String SetBBright;
    SharedPreferences lSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lamp_settings);
        brightnessR = findViewById(R.id.brightnessR);
        brightnessRValue = findViewById(R.id.brightnessRValue);
        brightnessB = findViewById(R.id.brightnessB);
        brightnesBValue = findViewById(R.id.brightnessBvalue);
        brightnessG = findViewById(R.id.brightnessG);
        brightnessGValue = findViewById(R.id.brightnessGValue);
        confirm = findViewById(R.id.confirm);
        getDefaultValue = findViewById(R.id.defaultValue);

        lSharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        final SharedPreferences.Editor editor = lSharedPreferences.edit();
        SetRBright = lSharedPreferences.getString(Util_String.LAMP_R_BRIGHTNESS,"147");
        SetGBright = lSharedPreferences.getString(Util_String.LAMP_G_BRIGHTNESS,"114");
        SetBBright = lSharedPreferences.getString(Util_String.LAMP_B_BRIGHTNESS,"110");
        brightnessR.setProgress(Integer.valueOf(SetRBright));
        brightnessG.setProgress(Integer.valueOf(SetGBright));
        brightnessB.setProgress(Integer.valueOf(SetBBright));
        brightnessRValue.setText("R value: " + SetRBright);
        brightnessGValue.setText("G value: " + SetGBright);
        brightnesBValue.setText("B value: " + SetBBright);
        /*Log.i("sharedR",SetRBright);
        Log.i("sharedG",SetGBright);
        Log.i("sharedB",SetRBright);*/

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*lampback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LampSettings.this,SettingMainActivity.class);
                startActivity(intent);
            }
        });*/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sent value;
                Intent messager = new Intent();
                messager.setAction("com.example.notificationblocker.BluetoothSerialService.MESSAGE");
                messager.putExtra("R",Integer.valueOf(SetRBright));
                messager.putExtra("G", Integer.valueOf(SetGBright));
                messager.putExtra("B", Integer.valueOf(SetBBright));
                sendBroadcast(messager);
            }
        });
        brightnessR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessRValue.setText("R value: " + String.valueOf(i));
                SetRBright = String.valueOf(i);
                editor.putString(Util_String.LAMP_R_BRIGHTNESS,String.valueOf(i)).apply();
        }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        brightnessG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessGValue.setText("G value: " + String.valueOf(i));
                SetGBright = String.valueOf(i);
                editor.putString(Util_String.LAMP_G_BRIGHTNESS,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        brightnessB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnesBValue.setText("B value: " + String.valueOf(i));
                SetBBright = String.valueOf(i);
                editor.putString(Util_String.LAMP_B_BRIGHTNESS,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        getDefaultValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(Util_String.LAMP_R_BRIGHTNESS,"147").apply();
                editor.putString(Util_String.LAMP_G_BRIGHTNESS,"114").apply();
                editor.putString(Util_String.LAMP_B_BRIGHTNESS,"110").apply();
                brightnessR.setProgress(147);
                brightnessG.setProgress(114);
                brightnessB.setProgress(110);
                brightnessRValue.setText("R value: " + "147");
                brightnessGValue.setText("G value: " + "114");
                brightnesBValue.setText("B value: " + "110");
            }
        });

    }
}
