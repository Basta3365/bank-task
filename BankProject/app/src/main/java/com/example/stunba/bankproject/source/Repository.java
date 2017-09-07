package com.example.stunba.bankproject.source;

import android.content.Context;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.local.LocalDataSource;




/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public class Repository {
    private static Repository INSTANCE = null;
    private final DataSource localDataSource;

    public DataSource getLocalDataSource() {
        return localDataSource;
    }

    private Repository(Context context) {
        localDataSource = new LocalDataSource(context);
    }

    public static Repository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
        return INSTANCE;
    }

    public void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        localDataSource.getAllCurrencies(dynamicPresenterCompleteCurrency);
    }

    public void getRateByAbb(String abb, OnTaskCompleted.MainPresenterComplete mainPresenterComplete) {
        localDataSource.getRateByAdd(abb, mainPresenterComplete);
    }

    public void getRateByDate(String val, String date, OnTaskCompleted.CalculatePresenterComplete calculatePresenterComplete) {
        localDataSource.getRateByDate(val, date, calculatePresenterComplete);
    }
    public void updateAllCurrencies(){
        localDataSource.updateAllCurrencies();
    }
    public void updateAllRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete){
        localDataSource.updateAllRates(mainPresenterComplete);
    }

    public void updateFavorites(OnTaskCompleted.FavoritePresenter favoritePresenter) {
        localDataSource.updateFavorites(favoritePresenter);
    }
    public void addFavorite(ActualRate favorite){
        localDataSource.addFavorite(favorite);
    }
    public void getAllMetalNames(OnTaskCompleted.LoadComplete onTaskCompleted){
        localDataSource.getAllMetalNames(onTaskCompleted);
    }
    public void getAllIngots(OnTaskCompleted.LoadComplete onTaskCompleted){
        localDataSource.getAllIngots(onTaskCompleted);
    }

    public void getRateCalculator(String abbFrom, String abbTo, double count, OnTaskCompleted.LoadComplete loadComplete) {
        localDataSource.getRateCalculator(abbFrom,abbTo,count,loadComplete);
    }
}
