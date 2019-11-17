package com.example.notificationlistener3;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SettingNotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PackageManager manager;

    private SearchView searchView;
    private ImageView empty;

    private SharedPreferences mPreferences;

    private List<ApplicationInfo> applications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_notification_activity);

        mPreferences = getSharedPreferences("setting",MODE_PRIVATE);
        manager = getPackageManager();
        applications = manager.getInstalledApplications(0);

        recyclerView = (RecyclerView) findViewById(R.id.appRecyclerView);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<ApplicationInfo> apps = manager.getInstalledApplications(0);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(SettingNotificationActivity.this, apps);
        recyclerView.setAdapter(mAdapter);
    }
}
