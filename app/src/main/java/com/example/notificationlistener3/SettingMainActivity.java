package com.example.notificationlistener3;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingMainActivity extends AppCompatActivity {
    Button buttonToNotifSetting;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main_activity);
        Log.i("SettingActivity", "oncreate");
        buttonToNotifSetting = findViewById(R.id.button);
        buttonToNotifSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingMainActivity.this, SettingNotificationActivity.class);
                startActivity(intent);
            }
        });
    }
}
