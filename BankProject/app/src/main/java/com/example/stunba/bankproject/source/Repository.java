package com.example.stunba.bankproject.source;

import android.content.Context;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.local.LocalDataSource;


/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public class Repository {
    private static Repository INSTANCE = null;
    private final DataSource localDataSource;

    private Repository(Context context) {
        localDataSource = new LocalDataSource(context);
    }

    public static Repository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
        return INSTANCE;
    }

    public void getAllCurrencies(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        localDataSource.getAllCurrencies(loadAllCurrencies);
    }

    public void getRateByAbb(String abb, OnTaskCompleted.LoadActualRate mainPresenterComplete) {
        localDataSource.getRateByAbb(abb, mainPresenterComplete);
    }

    public void getRateByDate(String val, String date, OnTaskCompleted.LoadActualRate loadRate) {
        localDataSource.getRateByDate(val, date, loadRate);
    }

    public void updateAllCurrencies() {
        localDataSource.updateAllCurrencies();
    }

    public void updateAllRates(OnTaskCompleted.LoadSuccessfully loadSuccessfully) {
        localDataSource.updateAllRates(loadSuccessfully);
    }

    public void updateFavorites(OnTaskCompleted.LoadFavoriteMap favoritePresenter) {
        localDataSource.updateFavorites(favoritePresenter);
    }

    public void addFavorite(ActualRate favorite) {
        localDataSource.addFavorite(favorite);
    }

    public void getAllMetalNames(OnTaskCompleted.MetalNamesLoadAll onTaskCompleted) {
        localDataSource.getAllMetalNames(onTaskCompleted);
    }

    public void getAllIngots(OnTaskCompleted.MetalLoadAll onTaskCompleted) {
        localDataSource.getAllIngots(onTaskCompleted);
    }

    public void getRateCalculator(String abbFrom, String abbTo, double count, OnTaskCompleted.LoadComplete loadComplete) {
        localDataSource.getRateCalculator(abbFrom, abbTo, count, loadComplete);
    }

    public void getAllFavorites(OnTaskCompleted.LoadAllActualRate loadComplete) {
        localDataSource.getAllFavorites(loadComplete);
    }

    public void deleteFavorite(ActualRate o) {
        localDataSource.deleteFavorite(o);
    }
}
