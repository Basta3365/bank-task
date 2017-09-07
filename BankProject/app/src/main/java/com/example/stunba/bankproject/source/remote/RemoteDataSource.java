package com.example.stunba.bankproject.source.remote;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.Settings;

import java.util.Calendar;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public class RemoteDataSource {
    private BankApi bankApi=new BankApi();
    public void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        bankApi.getAllCurrencies(dynamicPresenterCompleteCurrency);
    }

    public void getAllRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete){
        bankApi.getAllActualRate(mainPresenterComplete);
    }

    public void getRateByDate(String val, String date, OnTaskCompleted.CalculatePresenterComplete calculatePresenterComplete) {
        bankApi.getActualRateOnDate(val,date,calculatePresenterComplete);
    }

    public void getAllMetalNames(OnTaskCompleted.LoadComplete loadComplete) {
        bankApi.getAllMetalNames(loadComplete);
    }

    public void getAllIngots(OnTaskCompleted.LoadComplete loadComplete) {
        int year = Settings.CALENDAR.get(Calendar.YEAR);
        int month = Settings.CALENDAR.get(Calendar.MONTH);
        int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
        bankApi.getAllIngotsPricesOnDate(Settings.getDate(year,month,day),loadComplete);
    }
}
