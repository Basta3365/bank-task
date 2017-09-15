package com.example.stunba.bankproject.source.remote;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.Settings;

import java.util.Calendar;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public class RemoteDataSource {
    private BankApi bankApi = new BankApi();

    public void getAllCurrencies(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        bankApi.getAllCurrencies(loadAllCurrencies);
    }

    public void getAllRates(OnTaskCompleted.LoadAllActualRate loadAllActualRate) {
        bankApi.getAllActualRate(loadAllActualRate);
    }

    public void getRateByDate(String val, String date, OnTaskCompleted.LoadActualRate calculatePresenterComplete) {
        bankApi.getActualRateOnDate(val, date, calculatePresenterComplete);
    }

    public void getAllMetalNames(OnTaskCompleted.MetalNamesLoadAll loadComplete) {
        bankApi.getAllMetalNames(loadComplete);
    }

    public void getAllIngots(OnTaskCompleted.MetalLoadAll loadComplete) {
        int year = Settings.CALENDAR.get(Calendar.YEAR);
        int month = Settings.CALENDAR.get(Calendar.MONTH);
        int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
        bankApi.getAllIngotsPricesOnDate(Settings.getDate(year, month, day), loadComplete);
    }
}
