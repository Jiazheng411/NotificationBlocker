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

//This page describe the rest lamp settings of App
public class RestLampSettings extends AppCompatActivity{
    Button getDefaultValue;
    Button preview;
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
        setContentView(R.layout.rest_lamp_settings);

        brightnessR = findViewById(R.id.restR);
        brightnessRValue = findViewById(R.id.restRvalue);
        brightnessB = findViewById(R.id.restB);
        brightnesBValue = findViewById(R.id.restBvalue);
        brightnessG = findViewById(R.id.restG);
        brightnessGValue = findViewById(R.id.restGvalue);
        preview = findViewById(R.id.restPreview);
        getDefaultValue = findViewById(R.id.restDefalut);

        //lSharedPreference saves the value of RGB with an initial value of 237,220,128 which
        // can be modified by users later
        lSharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        final SharedPreferences.Editor editor = lSharedPreferences.edit();
        SetRBright = lSharedPreferences.getString(Util_String.LAMP_R_BRIGHTNESS_REST,"237");
        SetGBright = lSharedPreferences.getString(Util_String.LAMP_G_BRIGHTNESS_REST,"220");
        SetBBright = lSharedPreferences.getString(Util_String.LAMP_B_BRIGHTNESS_REST,"128");
        brightnessR.setProgress(Integer.valueOf(SetRBright));
        brightnessG.setProgress(Integer.valueOf(SetGBright));
        brightnessB.setProgress(Integer.valueOf(SetBBright));
        brightnessRValue.setText("R value: " + SetRBright);
        brightnessGValue.setText("G value: " + SetGBright);
        brightnesBValue.setText("B value: " + SetBBright);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Preview is the preview button on the page, after clicking, it will pass the RGB value to
        //the lamp to set a preview to users
        preview.setOnClickListener(new View.OnClickListener() {
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

        //Users change the R value by seekbar
        brightnessR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessRValue.setText("R value: " + String.valueOf(i));
                SetRBright = String.valueOf(i);
                editor.putString(Util_String.LAMP_R_BRIGHTNESS_REST,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Users change the G value by seekbar
        brightnessG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnessGValue.setText("G value: " + String.valueOf(i));
                SetGBright = String.valueOf(i);
                editor.putString(Util_String.LAMP_G_BRIGHTNESS_REST,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Users change the B value by seekbar
        brightnessB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightnesBValue.setText("B value: " + String.valueOf(i));
                SetBBright = String.valueOf(i);
                editor.putString(Util_String.LAMP_B_BRIGHTNESS_REST,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //We pre-set a default RGB value for users to use
        getDefaultValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(Util_String.LAMP_R_BRIGHTNESS_REST,"237").apply();
                editor.putString(Util_String.LAMP_G_BRIGHTNESS_REST,"220").apply();
                editor.putString(Util_String.LAMP_B_BRIGHTNESS_REST,"128").apply();
                brightnessR.setProgress(237);
                brightnessG.setProgress(220);
                brightnessB.setProgress(128);
                brightnessRValue.setText("R value: " + "237");
                brightnessGValue.setText("G value: " + "220");
                brightnesBValue.setText("B value: " + "128");
            }
        });



    }
}
