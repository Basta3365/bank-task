package com.example.stunba.bankproject.source.remote;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public interface IBankOperations {
    ActualRate getActualRate(String val, String periodicity);
    void getAllActualRate(OnTaskCompleted.MainPresenterComplete mainPresenterComplete);
    void getDynamicsPeriod( String val, String startDate,  String endDate,OnTaskCompleted.DynamicPresenterCompleteDynamic dynamicPresenterCompleteDynamic);
    void getActualRateOnDate( String val, String onDate,OnTaskCompleted.CalculatePresenterComplete calculatePresenterComplete);
    void getAllIngotsPricesOnDate(String onDate, OnTaskCompleted.LoadComplete loadComplete);
    List<ActualAllIngot> getIngotsPricesOnDate( String val,String onDate);
    void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency);
    void getAllMetalNames(OnTaskCompleted.LoadComplete loadComplete);

}
