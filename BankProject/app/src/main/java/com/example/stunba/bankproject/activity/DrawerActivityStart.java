package com.example.stunba.bankproject.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.fragments.FragmentAbout;
import com.example.stunba.bankproject.fragments.FragmentCalculator;
import com.example.stunba.bankproject.fragments.FragmentDynamicInfo;
import com.example.stunba.bankproject.fragments.FragmentFavorites;
import com.example.stunba.bankproject.fragments.FragmentCurrentExchangeRate;
import com.example.stunba.bankproject.fragments.FragmentRateOnDate;
import com.example.stunba.bankproject.fragments.RecyclerFragmentMetal;

public class DrawerActivityStart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new FragmentAbout();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_activity_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.one_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentCurrentExchangeRate()).commit();

        } else if (id == R.id.two_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentDynamicInfo()).commit();

        } else if (id == R.id.three_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentRateOnDate()).commit();

        } else if (id == R.id.four_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new RecyclerFragmentMetal()).commit();

        } else if (id == R.id.five_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentCalculator()).commit();

        } else if (id == R.id.six_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentFavorites()).commit();

        } else if (id == R.id.seven_screen) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentAbout()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
