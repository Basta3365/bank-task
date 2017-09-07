package com.example.stunba.bankproject.source;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public interface DataSource {
    void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency);
    void getRateByAdd(String abb,OnTaskCompleted.MainPresenterComplete mainPresenter);
    void getRateByDate(String val,String date,OnTaskCompleted.CalculatePresenterComplete calculatePresenterComplete);
    void getAllRates(OnTaskCompleted.MainPresenterComplete mainPresenter);
    void loadRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete);

    void updateAllCurrencies();

    void updateAllRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete);

    void updateFavorites(OnTaskCompleted.FavoritePresenter favoritePresenter);

    void addFavorite(ActualRate favorite);

    void getAllMetalNames(OnTaskCompleted.LoadComplete onTaskCompleted);

    void getAllIngots(OnTaskCompleted.LoadComplete onTaskCompleted);

    void getRateCalculator(String abbFrom, String abbTo, double count, OnTaskCompleted.LoadComplete loadComplete);
}
