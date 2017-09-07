package com.example.stunba.bankproject.source.remote;

import android.util.Log;

import com.example.stunba.bankproject.OnTaskCompleted;
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
    public ActualRate getActualRate(String val, String periodicity) {
        return null;
    }

    @Override
    public void getAllActualRate(final OnTaskCompleted.MainPresenterComplete mainPresenterComplete) {
        final List<ActualRate> allRates = new ArrayList<>();
        Settings.RETROFIT.create(IBankAPI.class).getAllActualRate("0").enqueue(new Callback<List<ActualRate>>() {
            @Override
            public void onResponse(Call<List<ActualRate>> call, Response<List<ActualRate>> response) {
                allRates.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<ActualRate>> call, Throwable t) {

            }
        });
        Settings.RETROFIT.create(IBankAPI.class).getAllActualRate("1").enqueue(new Callback<List<ActualRate>>() {
            @Override
            public void onResponse(Call<List<ActualRate>> call, Response<List<ActualRate>> response) {
                allRates.addAll(response.body());
                mainPresenterComplete.onLoadRate(allRates);

            }

            @Override
            public void onFailure(Call<List<ActualRate>> call, Throwable t) {
                if(allRates.size()==0) {
                    mainPresenterComplete.onLoadRate(null);
                }
            }
        });
    }

    @Override
    public void getDynamicsPeriod(String val, String startDate, String endDate, final OnTaskCompleted.DynamicPresenterCompleteDynamic dynamicPresenterCompleteDynamic) {
        Settings.RETROFIT.create(IBankAPI.class).getDynamicsPeriod(val, startDate, endDate).enqueue(new Callback<List<DynamicPeriod>>() {
            @Override
            public void onResponse(Call<List<DynamicPeriod>> call, Response<List<DynamicPeriod>> response) {
                dynamicPresenterCompleteDynamic.onDynamicLoad(response.body());
            }

            @Override
            public void onFailure(Call<List<DynamicPeriod>> call, Throwable t) {
                Log.d("BANKAPI", "Failed");
            }
        });
    }

    @Override
    public void getActualRateOnDate(String val, String onDate, final OnTaskCompleted.CalculatePresenterComplete calculatePresenterComplete) {
        Settings.RETROFIT.create(IBankAPI.class).getActualRateOnDate(val, onDate).enqueue(new Callback<ActualRate>() {
            @Override
            public void onResponse(Call<ActualRate> call, Response<ActualRate> response) {
                calculatePresenterComplete.onLoadRateByDate(response.body());

            }

            @Override
            public void onFailure(Call<ActualRate> call, Throwable t) {
                calculatePresenterComplete.onLoadRateByDate(null);
            }
        });
    }

    @Override
    public void getAllIngotsPricesOnDate(String onDate, final OnTaskCompleted.LoadComplete loadComplete) {
        Settings.RETROFIT.create(IBankAPI.class).getAllIngotsPricesOnDate(onDate).enqueue(new Callback<List<ActualAllIngot>>() {
            @Override
            public void onResponse(Call<List<ActualAllIngot>> call, Response<List<ActualAllIngot>> response) {
                loadComplete.onLoadComplete(response.body());
            }

            @Override
            public void onFailure(Call<List<ActualAllIngot>> call, Throwable t) {
                loadComplete.onLoadComplete(null);
            }
        });
    }

    @Override
    public List<ActualAllIngot> getIngotsPricesOnDate(String val, String onDate) {
        return null;
    }

    @Override
    public void getAllCurrencies(final OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency) {
        Settings.RETROFIT.create(IBankAPI.class).getAllCurrencies().enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                dynamicPresenterCompleteCurrency.onAllCurrencyLoad(response.body());
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getAllMetalNames(final OnTaskCompleted.LoadComplete loadComplete) {
        Settings.RETROFIT.create(IBankAPI.class).getAllMetalNames().enqueue(new Callback<List<MetalName>>() {
            @Override
            public void onResponse(Call<List<MetalName>> call, Response<List<MetalName>> response) {
                loadComplete.onLoadComplete(response.body());
            }

            @Override
            public void onFailure(Call<List<MetalName>> call, Throwable t) {
                loadComplete.onLoadComplete(null);
            }
        });
    }

}
