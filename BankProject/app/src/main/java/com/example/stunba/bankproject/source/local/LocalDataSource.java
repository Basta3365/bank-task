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
    private DatabaseHandlerActualRate databaseHandlerActualRate;
    private final DatabaseHandlerMetalName databaseHandlerMetalName;
    private final DatabaseHandlerCurrency databaseHandlerCurrency;
    private final DatabaseHandlerMetalRate databaseHandlerMetalRate;
    private final DatabaseHandlerFavorites databaseHandlerFavorites;

    public LocalDataSource(Context context) {
        databaseHandlerActualRate = new DatabaseHandlerActualRate(context);
        databaseHandlerCurrency = new DatabaseHandlerCurrency(context);
        databaseHandlerMetalName = new DatabaseHandlerMetalName(context);
        databaseHandlerMetalRate = new DatabaseHandlerMetalRate(context);
        databaseHandlerFavorites = new DatabaseHandlerFavorites(context);
    }

    @Override
    public void getAllCurrencies(OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        databaseHandlerCurrency.getAllCurrencies(loadAllCurrencies);
    }

    public void getRateByDate(String val, String date, OnTaskCompleted.LoadActualRate calculatePresenterComplete) {
        databaseHandlerActualRate.getRateByDate(val, date, calculatePresenterComplete);
    }

    @Override
    public void getAllRates(OnTaskCompleted.LoadAllActualRate mainPresenter) {
        databaseHandlerActualRate.getAllRates(mainPresenter);
    }

    @Override
    public void loadRates(OnTaskCompleted.LoadAllActualRate loadAllActualRate) {
        databaseHandlerActualRate.loadAllRate(loadAllActualRate);
    }

    @Override
    public void updateAllCurrencies() {
        databaseHandlerCurrency.deleteAll();
        databaseHandlerCurrency.loadAllCurrency(new OnTaskCompleted.LoadAllCurrencies() {
            @Override
            public void onAllCurrencyLoad(List<Currency> o) {
                if (o != null) {
                    databaseHandlerCurrency.addLoadCurrencies(o);
                }
            }
        });
    }

    @Override
    public void updateAllRates(final OnTaskCompleted.LoadSuccessfully loadSuccessfully) {
        databaseHandlerActualRate.deleteAll();
        databaseHandlerActualRate.loadAllRate(new OnTaskCompleted.LoadAllActualRate() {
            @Override
            public void onLoadAllRate(List<ActualRate> o) {
                if (o != null) {
                    for (ActualRate actual : o) {
                        databaseHandlerActualRate.addRate(actual);
                    }
                    loadSuccessfully.onLoadSuccess(true);
                }
            }
        });
    }

    @Override
    public void updateFavorites(final OnTaskCompleted.LoadFavoriteMap favoritePresenter) {
        final List<ActualRate> favorites = new ArrayList<>();
        final Map<String, Double> changes = new HashMap<>();
        databaseHandlerFavorites.getAllFavorites(new OnTaskCompleted.LoadAllActualRate() {
            @Override
            public void onLoadAllRate(List<ActualRate> o) {
                favorites.addAll(o);
                final int count = favorites.size();
                for (int i = 0; i < count; i++) {
                    final int finalI = i;
                    databaseHandlerActualRate.getRateByAbb(favorites.get(i).getCurAbbreviation(), new OnTaskCompleted.LoadActualRate() {
                        @Override
                        public void onLoadRate(ActualRate temp) {
                            if (temp != null) {
                                if (temp.getCurOfficialRate() < favorites.get(finalI).getCurOfficialRate()) {
                                    changes.put(temp.getCurAbbreviation(), temp.getCurOfficialRate() - favorites.get(finalI).getCurOfficialRate());
                                }
                                databaseHandlerFavorites.updateFavorite(temp);
                                if (finalI == count - 1) {
                                    favoritePresenter.onLoadMap(changes);
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
    public void getAllMetalNames(OnTaskCompleted.MetalNamesLoadAll onTaskCompleted) {
        databaseHandlerMetalName.getAllMetal(onTaskCompleted);
    }

    @Override
    public void getAllIngots(OnTaskCompleted.MetalLoadAll onTaskCompleted) {
        databaseHandlerMetalRate.getAllIngots(onTaskCompleted);
    }

    @Override
    public void getRateCalculator(String abbFrom, String abbTo, final double count, final OnTaskCompleted.LoadComplete loadComplete) {
        if (abbFrom.equals("BYR")) {
            databaseHandlerActualRate.getRateByAbb(abbTo, new OnTaskCompleted.LoadActualRate() {
                @Override
                public void onLoadRate(ActualRate rate) {
                    if (rate != null) {
                        double answer = (count * rate.getCurScale()) / rate.getCurOfficialRate();
                        loadComplete.onLoadComplete(answer);
                    } else {
                        loadComplete.onLoadComplete(-1);
                    }
                }
            });
        }
        if (abbTo.equals("BYR")) {
            databaseHandlerActualRate.getRateByAbb(abbFrom, new OnTaskCompleted.LoadActualRate() {
                @Override
                public void onLoadRate(ActualRate rate) {
                    if (rate != null) {
                        double answer = (count * rate.getCurOfficialRate()) / rate.getCurScale();
                        loadComplete.onLoadComplete(answer);
                    } else {
                        loadComplete.onLoadComplete(-1);
                    }
                }
            });
        }
    }

    @Override
    public void getAllFavorites(OnTaskCompleted.LoadAllActualRate loadComplete) {
        databaseHandlerFavorites.getAllFavorites(loadComplete);
    }

    @Override
    public void deleteFavorite(ActualRate o) {
        databaseHandlerFavorites.deleteFavorite(o);
    }

    public void getRateByAbb(String abb, OnTaskCompleted.LoadActualRate mainPresenter) {
        databaseHandlerActualRate.getRateByAbb(abb, mainPresenter);
    }

}
