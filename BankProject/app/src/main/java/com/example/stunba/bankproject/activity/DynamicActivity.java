package com.example.stunba.bankproject.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.fragments.FragmentDynamicInfo;

public class DynamicActivity extends AppCompatActivity {
    private String abb;
    private Double cheaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        Intent intent = getIntent();
        abb = intent.getStringExtra("abb");
        cheaper = intent.getDoubleExtra("cheaper", 0);
        int id = intent.getIntExtra("id", 0);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_dynamic);
        if (fragment == null) {
            fragment = new FragmentDynamicInfo();
            Bundle bundle = new Bundle();
            bundle.putString("abb", abb);
            bundle.putDouble("cheaper", cheaper);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fragment_container_dynamic, fragment).commit();
        }

    }
}
