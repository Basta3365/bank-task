package com.example.stunba.bankproject.source;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public interface DataSource {
    void getAllCurrencies(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies);

    void getRateByAbb(String abb, OnTaskCompleted.LoadActualRate mainPresenter);

    void getRateByDate(String val, String date, OnTaskCompleted.LoadActualRate calculatePresenterComplete);

    void getAllRates(OnTaskCompleted.LoadAllActualRate mainPresenter);

    void loadRates(OnTaskCompleted.LoadAllActualRate loadAllActualRate);

    void updateAllCurrencies();

    void updateAllRates(OnTaskCompleted.LoadSuccessfully loadSuccessfully);

    void updateFavorites( OnTaskCompleted.LoadFavoriteMap favoritePresenter);

    void addFavorite(ActualRate favorite);

    void getAllMetalNames(OnTaskCompleted.MetalNamesLoadAll onTaskCompleted);

    void getAllIngots(OnTaskCompleted.MetalLoadAll onTaskCompleted);

    void getRateCalculator(String abbFrom, String abbTo, double count, OnTaskCompleted.LoadComplete loadComplete);

    void getAllFavorites(OnTaskCompleted.LoadAllActualRate loadComplete);

    void deleteFavorite(ActualRate o);
}
