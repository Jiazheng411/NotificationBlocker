package com.example.notificationlistener3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

//This page describe the time setting of App
public class TimeSettings extends AppCompatActivity{
    SeekBar studyTime;
    SeekBar restTime;
    TextView StudyTimevalue;
    TextView RestTimevalue;
    Button timedefault;
    String SetStudyTime;
    String SetRestTime;
    SharedPreferences tSaredPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_settings);
        studyTime = findViewById(R.id.studyTime);
        restTime = findViewById(R.id.restTime);
        StudyTimevalue = findViewById(R.id.studyTimeValue);
        RestTimevalue = findViewById(R.id.resttimeValue);
        timedefault = findViewById(R.id.timeDefault);

        //tSharedPreference saves the value of study time and rest time with an initial value
        // of 60 and 15 which can be modified by users later
        tSaredPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        final SharedPreferences.Editor editor = tSaredPreferences.edit();
        editor.putString(Util_String.CHANGEING_TIMING_SETTING, "true");
        SetRestTime = tSaredPreferences.getString(Util_String.RESTING_TIME,"15");
        SetStudyTime = tSaredPreferences.getString(Util_String.FOCUS_TIME,"60");
        Log.i("Time settings", "resttime"+SetRestTime);
        Log.i("Time settings", "focus time"+SetStudyTime);
        studyTime.setProgress(Integer.valueOf(SetStudyTime));
        restTime.setProgress(Integer.valueOf(SetRestTime));
        StudyTimevalue.setText(SetStudyTime);
        RestTimevalue.setText(SetRestTime);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //We set a defalut value of study and rest time for users to use
        timedefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(Util_String.RESTING_TIME,"15");
                editor.putString(Util_String.FOCUS_TIME,"60");
                editor.apply();
                studyTime.setProgress(60);
                restTime.setProgress(15);

            }
        });
        //Users can change the study time they want
        studyTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                StudyTimevalue.setText(String.valueOf(i));
                //SetStudyTime = String.valueOf(i);
                editor.putString(Util_String.FOCUS_TIME,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Users can change the rest time they want
        restTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                RestTimevalue.setText(String.valueOf(i));
                //SetRestTime = String.valueOf(i);
                editor.putString(Util_String.RESTING_TIME,String.valueOf(i)).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
