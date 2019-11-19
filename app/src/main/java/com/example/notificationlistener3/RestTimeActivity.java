package com.example.notificationlistener3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RestTimeActivity extends AppCompatActivity {
    ImageView buttonSetting;
    Button buttonStartFocusMode;
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_time_activity_land);
        Log.i("RestTimeActivity", "oncreate");
        buttonSetting = findViewById(R.id.buttonSetting);
        buttonStartFocusMode = findViewById(R.id.buttonStartFocusModeRestPage);

        mSharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        boolean is_blocking = false;
        editor.putBoolean(Util_String.IS_BLOCKING, is_blocking);
        editor.apply();
        Log.i("MainActivity", "edit shared preference is_blocking, not blocking notification");

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestTimeActivity.this, SettingMainActivity.class);
                startActivity(intent);
            }
        });

        buttonStartFocusMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( RestTimeActivity.this, FocusModeActivity.class );
                startActivity( intent );
            }
        });
    }
}
