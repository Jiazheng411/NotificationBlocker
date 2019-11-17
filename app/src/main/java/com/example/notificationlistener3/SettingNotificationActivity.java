package com.example.notificationlistener3;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SettingNotificationActivity extends AppCompatActivity implements onCheckboxClicked{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PackageManager manager;

    private SearchView searchView;

    private SharedPreferences mPreferences;

    private List<ApplicationInfo> applications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_notification_activity);

        mPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        manager = getPackageManager();
        applications = manager.getInstalledApplications(0);

        recyclerView = findViewById(R.id.appRecyclerView);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        applications = manager.getInstalledApplications(0);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(SettingNotificationActivity.this, applications, this);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void OnCheckboxClicked(int pos, boolean isChecked){
        String appname = applications.get(pos).packageName;
        HashSet<String> appsNotBlocking = new HashSet<>(Arrays.asList(mPreferences.getString(Util_String.APPS_RECEIVing_NOTIFICATION, "").split(";")));

        if (isChecked) {
            appsNotBlocking.add(appname);
        } else {
            appsNotBlocking.remove(appname);
        }

        mPreferences.edit().putString(Util_String.APPS_RECEIVing_NOTIFICATION, TextUtils.join(";", appsNotBlocking)).apply();

    }
}
