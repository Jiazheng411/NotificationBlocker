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

public class TimeSettings extends AppCompatActivity{
    SeekBar studyTime;
    SeekBar restTime;
    TextView StudyTimevalue;
    TextView RestTimevalue;
    Button timeback;
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
        timeback = findViewById(R.id.timeBack);

        timeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeSettings.this, SettingMainActivity.class);
                startActivity(intent);
            }
        });
        studyTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                StudyTimevalue.setText(String.valueOf(i));
                SetStudyTime = String.valueOf(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        restTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                RestTimevalue.setText(String.valueOf(i));
                SetRestTime = String.valueOf(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tSaredPreferences = getSharedPreferences("time settings",MODE_PRIVATE);
        SharedPreferences.Editor editor = tSaredPreferences.edit();
        editor.putString("RESTING_TIME",SetRestTime).apply();
        editor.putString("FOUCUS_TIME",SetStudyTime).apply();

    }
}
