package com.example.stunba.bankproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stunba.bankproject.fragments.FragmentCalculator;
import com.example.stunba.bankproject.fragments.FragmentDynamicInfo;
import com.example.stunba.bankproject.fragments.FragmentFavorites;
import com.example.stunba.bankproject.fragments.RecyclerFragmentMetal;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentOneScreen()).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentDynamicInfo()).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentFavorites()).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentTreeScreen()).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new RecyclerFragmentMetal()).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new FragmentCalculator()).commit();
        FragmentManager fragmentManager=getSupportFragmentManager();

        Fragment fragment =fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment==null) {
//            fragment=new FragmentCalculator();
            fragment=new FragmentFavorites();
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();

        }
//        }




    }
}
