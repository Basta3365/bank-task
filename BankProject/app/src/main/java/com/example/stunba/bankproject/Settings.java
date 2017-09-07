package com.example.stunba.bankproject;

import java.util.Calendar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kseniya_Bastun on 8/24/2017.
 */

public class Settings {
    public static Retrofit RETROFIT= new Retrofit.Builder().baseUrl("http://www.nbrb.by/API/").addConverterFactory(GsonConverterFactory.create()).build();
    public static String USD="145";
    public static String RUB="298";
    public static String EUR="292";
    public static Calendar CALENDAR = Calendar.getInstance();
    public static String getDate(int year, int month, int dayOfMonth) {
        String sMonth = String.valueOf(month + 1);
        String sDay = String.valueOf(dayOfMonth);
        return year + "-" + sMonth + "-" + sDay;

    }
}
