package com.example.stunba.bankproject.source.local;

import android.content.Context;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.DataSource;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public class LocalDataSource implements DataSource {
    private final DatabaseHandler databaseHandler;
    private final DatabaseHandlerMetalName databaseHandlerMetalName;
    private final DatabaseHandlerCurrency databaseHandlerCurrency;
    private final DatabaseHandlerMetalRate databaseHandlerMetalRate;
    private final DatabaseHandlerFavorites databaseHandlerFavorites;

    public LocalDataSource(Context context) {
        databaseHandler = new DatabaseHandler(context);
        databaseHandlerCurrency = new DatabaseHandlerCurrency(context);
        databaseHandlerMetalName = new DatabaseHandlerMetalName(context);
        databaseHandlerMetalRate = new DatabaseHandlerMetalRate(context);
        databaseHandlerFavorites = new DatabaseHandlerFavorites(context);
    }

    @Override
    public void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        databaseHandlerCurrency.getAllCurrencies(dynamicPresenterCompleteCurrency);
    }

    public void getRateByDate(String val, String date, OnTaskCompleted.CalculatePresenterComplete calculatePresenterComplete) {
        databaseHandler.getRateByDate(val, date, calculatePresenterComplete);
    }

    @Override
    public void getAllRates(OnTaskCompleted.MainPresenterComplete mainPresenter) {
        databaseHandler.getAllRates(mainPresenter);
    }

    @Override
    public void loadRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete) {
        databaseHandler.loadAllRate(mainPresenterComplete);
    }

    @Override
    public void updateAllCurrencies() {
        databaseHandlerCurrency.deleteAll();
        databaseHandlerCurrency.loadAllCurrency(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
            @Override
            public void onAllCurrencyLoad(Object o) {
                if (o != null) {
                    for (Currency cur : (List<Currency>) o) {
                        databaseHandlerCurrency.addCurrency(cur);
                    }
                }
            }
        });
    }

    @Override
    public void updateAllRates(final OnTaskCompleted.MainPresenterComplete mainPresenterComplete) {
        databaseHandler.deleteAll();
        databaseHandler.loadAllRate(new OnTaskCompleted.MainPresenterComplete() {
            @Override
            public void onLoadRate(Object o) {
                if (o != null) {
                    for (ActualRate actual : (List<ActualRate>) o) {
                        databaseHandler.addRate(actual);
                    }
                    mainPresenterComplete.onLoadRate("Success");
                }
            }
        });
    }

    @Override
    public void updateFavorites(final OnTaskCompleted.FavoritePresenter favoritePresenter) {
        final List<ActualRate> favorites = new ArrayList<>();
        final Map<String, Double> changes = new HashMap<>();
            databaseHandlerFavorites.getAllFavorites(new OnTaskCompleted.FavoritePresenter() {
                @Override
                public void onAllFavorites(Object o) {
                    favorites.addAll((List<ActualRate>) o);
                    for (final ActualRate favorite : favorites) {
                        databaseHandler.getRateByAbb(favorite.getCurAbbreviation(), new OnTaskCompleted.MainPresenterComplete() {
                            @Override
                            public void onLoadRate(Object o) {
                                if(o!=null) {
                                    ActualRate temp = (ActualRate) o;
                                    if (temp.getCurOfficialRate() < favorite.getCurOfficialRate()) {
                                        changes.put(temp.getCurAbbreviation(), temp.getCurOfficialRate() - favorite.getCurOfficialRate());
                                    }
                                    databaseHandlerFavorites.updateFavorite(temp);
                                    favoritePresenter.onAllFavorites(changes);
                                }
                            }
                        });
                    }
                    //TODO right
                }
            });

    }

    @Override
    public void addFavorite(ActualRate favorite) {
        databaseHandlerFavorites.addFavorite(favorite);
    }

    @Override
    public void getAllMetalNames(OnTaskCompleted.LoadComplete onTaskCompleted) {
        databaseHandlerMetalName.getAllMetal(onTaskCompleted);
    }

    @Override
    public void getAllIngots(OnTaskCompleted.LoadComplete onTaskCompleted) {
        databaseHandlerMetalRate.getAllIngots(onTaskCompleted);
//        databaseHandler.deleteAll();
    }

    public void getRateByAdd(String abb, OnTaskCompleted.MainPresenterComplete mainPresenter) {
        databaseHandler.getRateByAbb(abb, mainPresenter);
    }

}
