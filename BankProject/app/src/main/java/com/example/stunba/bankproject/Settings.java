package com.example.stunba.bankproject;

import com.example.stunba.bankproject.source.remote.IBankAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public static List<String> listCurrency=new ArrayList<String>() {{
        add("USD");
        add("EUR");
        add("RUB");
    }};

    public static String START_DATE="startDate";
    public static String END_DATE="endDate";
    public static String DATE="date";
    public static String ACTUAL_RATE="actual_rate";
    public static String RATE="rate";
    public static String ABBREVIATION="abb";
    public static String NOTIFICATION_ID="id";
    public static String CHEAPER="cheaper";
    public static String getDate(int year, int month, int dayOfMonth) {
        String sMonth = String.valueOf(month + 1);
        String sDay = String.valueOf(dayOfMonth);
        return year + "-" + sMonth + "-" + sDay;
    }
}
