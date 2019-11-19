package com.example.notificationlistener3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    Button lampback;
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
        lampback = findViewById(R.id.lampBack);
        brightnessR = findViewById(R.id.brightnessR);
        brightnessRValue = findViewById(R.id.brightnessRValue);
        brightnessB = findViewById(R.id.brightnessB);
        brightnesBValue = findViewById(R.id.brightnessBvalue);
        brightnessG = findViewById(R.id.brightnessG);
        brightnessGValue = findViewById(R.id.brightnessGValue);
        confirm = findViewById(R.id.confirm);


        lampback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LampSettings.this,SettingMainActivity.class);
                startActivity(intent);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sent value;
            }
        });
        brightnessR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessRValue.setText("R value: " + String.valueOf(i));
                SetRBright = String.valueOf(i);
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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lSharedPreferences = getSharedPreferences("lampSettings",MODE_PRIVATE);
        SharedPreferences.Editor editor = lSharedPreferences.edit();
        editor.putString("LAMP_R_BRIGTNESS",SetRBright).apply();
        editor.putString("LAMP_G_BRIGTNESS",SetGBright).apply();
        editor.putString("LAMP_B_BRIGHTNESS",SetBBright).apply();

    }
}
