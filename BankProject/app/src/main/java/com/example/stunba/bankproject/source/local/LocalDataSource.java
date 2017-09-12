package com.example.stunba.bankproject.source.local;

import android.content.Context;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
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
                final int count = favorites.size();
                for (int i = 0; i < count; i++) {
                    final int finalI = i;
                    databaseHandler.getRateByAbb(favorites.get(i).getCurAbbreviation(), new OnTaskCompleted.MainPresenterComplete() {
                        @Override
                        public void onLoadRate(Object o) {
                            if (o != null) {
                                ActualRate temp = (ActualRate) o;
                                if (temp.getCurOfficialRate() < favorites.get(finalI).getCurOfficialRate()) {
                                    changes.put(temp.getCurAbbreviation(), temp.getCurOfficialRate() - favorites.get(finalI).getCurOfficialRate());
                                }
                                databaseHandlerFavorites.updateFavorite(temp);
                                if (finalI == count - 1) {
                                    favoritePresenter.onAllFavorites(changes);
                                }
                            }
                        }
                    });
                }
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
    }

    @Override
    public void getRateCalculator(String abbFrom, String abbTo, final double count, final OnTaskCompleted.LoadComplete loadComplete) {
        if (abbFrom.equals("BYR")) {
            databaseHandler.getRateByAbb(abbTo, new OnTaskCompleted.MainPresenterComplete() {
                @Override
                public void onLoadRate(Object o) {
                    ActualRate rate = (ActualRate) o;
                    double answer = 0;
                    answer = (count * rate.getCurScale()) / rate.getCurOfficialRate();
                    loadComplete.onLoadComplete(answer);
                }
            });
        }
        if (abbTo.equals("BYR")) {
            databaseHandler.getRateByAbb(abbFrom, new OnTaskCompleted.MainPresenterComplete() {
                @Override
                public void onLoadRate(Object o) {
                    ActualRate rate = (ActualRate) o;
                    double answer = 0;
                    answer = (count * rate.getCurOfficialRate()) / rate.getCurScale();
                    loadComplete.onLoadComplete(answer);
                }
            });
        }
    }

    @Override
    public void getAllFavorites(OnTaskCompleted.FavoritePresenter loadComplete) {
        databaseHandlerFavorites.getAllFavorites(loadComplete);
    }

    @Override
    public void deleteFavorite(ActualRate o) {
        databaseHandlerFavorites.deleteFavorite(o);
    }

    public void getRateByAdd(String abb, OnTaskCompleted.MainPresenterComplete mainPresenter) {
        databaseHandler.getRateByAbb(abb, mainPresenter);
    }

}
