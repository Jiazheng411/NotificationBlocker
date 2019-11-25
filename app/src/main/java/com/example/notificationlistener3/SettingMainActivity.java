package com.example.notificationlistener3;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingMainActivity extends AppCompatActivity {
    Button lampButton;
    Button timeButton;
    Button buttonToNotifSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        lampButton = findViewById(R.id.lampSetting);
        timeButton = findViewById(R.id.timeSettings);
        buttonToNotifSetting = findViewById(R.id.buttonNotificationSetting);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingMainActivity.this, LampSettings.class);
                startActivity(intent);
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingMainActivity.this, TimeSettings.class);
                startActivity(intent);

                Log.i("SettingActivity", "oncreate");
            }
        });

        buttonToNotifSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingMainActivity.this, SettingNotificationActivity.class);
                startActivity(intent);
            }
        });

    }
}
