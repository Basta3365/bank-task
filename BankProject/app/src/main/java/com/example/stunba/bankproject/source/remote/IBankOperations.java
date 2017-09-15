package com.example.stunba.bankproject.source.remote;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public interface IBankOperations {

    void getAllActualRate(OnTaskCompleted.LoadAllActualRate loadAllActualRate);

    void getDynamicsPeriod(String val, String startDate, String endDate, OnTaskCompleted.DynamicPresenterCompleteDynamic dynamicPresenterCompleteDynamic);

    void getActualRateOnDate(String val, String onDate, OnTaskCompleted.LoadActualRate calculatePresenterComplete);

    void getAllIngotsPricesOnDate(String onDate, OnTaskCompleted.MetalLoadAll loadComplete);

    void getAllCurrencies(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies);

    void getAllMetalNames(OnTaskCompleted.MetalNamesLoadAll loadComplete);

}
