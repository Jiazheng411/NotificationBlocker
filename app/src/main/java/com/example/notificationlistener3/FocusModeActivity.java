package com.example.notificationlistener3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FocusModeActivity extends AppCompatActivity {
    ImageView settingButton;
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("FocusModeActivity", "oncreate");
        setContentView(R.layout.focus_mode_activity);
        settingButton = findViewById(R.id.buttonSetting);

        mSharedPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        boolean contains = mSharedPreferences.contains(Util_String.IS_BLOCKING);
        Log.i("FocusMode", "Sharedpreferecne contains key is_blocking" + contains);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        boolean is_blocking = true;
        editor.putBoolean(Util_String.IS_BLOCKING, is_blocking);
        editor.apply();
        Log.i("MainActivity", "edit shared preference is_blocking, blocking notification");

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FocusModeActivity.this, SettingMainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
