package com.example.stunba.bankproject.source.remote;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.Settings;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.example.stunba.bankproject.source.entities.MetalName;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kseniya_Bastun on 8/30/2017.
 */

public class BankApi implements IBankOperations {


    @Override
    public void getAllActualRate(final OnTaskCompleted.LoadAllActualRate loadAllActualRate) {
        final List<ActualRate> allRates = new ArrayList<>();
        Settings.RETROFIT.getAllActualRate("0").enqueue(new Callback<List<ActualRate>>() {
            @Override
            public void onResponse(Call<List<ActualRate>> call, Response<List<ActualRate>> response) {
                allRates.addAll(response.body());
                Settings.RETROFIT.getAllActualRate("1").enqueue(new Callback<List<ActualRate>>() {
                    @Override
                    public void onResponse(Call<List<ActualRate>> call, Response<List<ActualRate>> response) {
                        allRates.addAll(response.body());
                        loadAllActualRate.onLoadAllRate(allRates);

                    }

                    @Override
                    public void onFailure(Call<List<ActualRate>> call, Throwable t) {
                        loadAllActualRate.onLoadAllRate(allRates);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ActualRate>> call, Throwable t) {
                Settings.RETROFIT.getAllActualRate("1").enqueue(new Callback<List<ActualRate>>() {
                    @Override
                    public void onResponse(Call<List<ActualRate>> call, Response<List<ActualRate>> response) {
                        allRates.addAll(response.body());
                        loadAllActualRate.onLoadAllRate(allRates);

                    }

                    @Override
                    public void onFailure(Call<List<ActualRate>> call, Throwable t) {
                        loadAllActualRate.onLoadAllRate(null);
                    }
                });
            }
        });
    }

    @Override
    public void getDynamicsPeriod(String val, String startDate, String endDate, final OnTaskCompleted.DynamicPresenterCompleteDynamic dynamicPresenterCompleteDynamic) {
        Settings.RETROFIT.getDynamicsPeriod(val, startDate, endDate).enqueue(new Callback<List<DynamicPeriod>>() {
            @Override
            public void onResponse(Call<List<DynamicPeriod>> call, Response<List<DynamicPeriod>> response) {
                dynamicPresenterCompleteDynamic.onDynamicLoad(response.body());
            }

            @Override
            public void onFailure(Call<List<DynamicPeriod>> call, Throwable t) {
                dynamicPresenterCompleteDynamic.onDynamicLoad(null);
            }
        });
    }

    @Override
    public void getActualRateOnDate(String val, String onDate, final OnTaskCompleted.LoadActualRate calculatePresenterComplete) {
        Settings.RETROFIT.getActualRateOnDate(val, onDate).enqueue(new Callback<ActualRate>() {
            @Override
            public void onResponse(Call<ActualRate> call, Response<ActualRate> response) {
                calculatePresenterComplete.onLoadRate(response.body());

            }

            @Override
            public void onFailure(Call<ActualRate> call, Throwable t) {
                calculatePresenterComplete.onLoadRate(null);
            }
        });
    }

    @Override
    public void getAllIngotsPricesOnDate(String onDate, final OnTaskCompleted.MetalLoadAll loadComplete) {
        Settings.RETROFIT.getAllIngotsPricesOnDate(onDate).enqueue(new Callback<List<ActualAllIngot>>() {
            @Override
            public void onResponse(Call<List<ActualAllIngot>> call, Response<List<ActualAllIngot>> response) {
                loadComplete.onAllIngot(response.body());
            }

            @Override
            public void onFailure(Call<List<ActualAllIngot>> call, Throwable t) {
                loadComplete.onAllIngot(null);
            }
        });
    }

    @Override
    public void getAllCurrencies(final OnTaskCompleted.LoadAllCurrencies loadAllCurrencies) {
        Settings.RETROFIT.getAllCurrencies().enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                loadAllCurrencies.onAllCurrencyLoad(response.body());
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
                loadAllCurrencies.onAllCurrencyLoad(null);
            }
        });
    }

    @Override
    public void getAllMetalNames(final OnTaskCompleted.MetalNamesLoadAll loadComplete) {
        Settings.RETROFIT.getAllMetalNames().enqueue(new Callback<List<MetalName>>() {
            @Override
            public void onResponse(Call<List<MetalName>> call, Response<List<MetalName>> response) {
                loadComplete.onAllNames(response.body());
            }

            @Override
            public void onFailure(Call<List<MetalName>> call, Throwable t) {
                loadComplete.onAllNames(null);
            }
        });
    }

}
