package com.example.stunba.bankproject;

import com.example.stunba.bankproject.source.remote.IBankAPI;

import java.util.Calendar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kseniya_Bastun on 8/24/2017.
 */

public class Settings {
    public final static IBankAPI RETROFIT = new Retrofit.Builder()
            .baseUrl("http://www.nbrb.by/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IBankAPI.class);
    public static int COUNT = 100;
    public static final int SERVICE_ID = 1;
    public final static Calendar CALENDAR = Calendar.getInstance();

    public static String getDate(int year, int month, int dayOfMonth) {
        String sMonth = String.valueOf(month + 1);
        String sDay = String.valueOf(dayOfMonth);
        return year + "-" + sMonth + "-" + sDay;
    }
}
