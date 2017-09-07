package com.example.stunba.bankproject.source.remote;

import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by stunba on 8/24/17.
 */

public interface IBankAPI {
    @GET("ExRates/Rates/{val}")
    Call<ActualRate> getActualRate(@Path("val") String val, @Query("Periodicity") String periodicity);
    @GET("ExRates/Rates")
    Call<List<ActualRate>> getAllActualRate( @Query("Periodicity") String periodicity);
    @GET("ExRates/Rates/Dynamics/{val}")
    Call<List<DynamicPeriod>> getDynamicsPeriod(@Path("val") String val, @Query("startDate") String startDate, @Query("endDate") String endDate);
    @GET("ExRates/Rates/{val}")
    Call<ActualRate> getActualRateOnDate(@Path("val") String val,@Query("onDate") String onDate);
    @GET("Ingots/Prices")
    Call<List<ActualAllIngot>> getAllIngotsPricesOnDate(@Query("onDate") String onDate);
    @GET("Ingots/Prices/{val}")
    Call<List<ActualAllIngot>> getIngotsPricesOnDate(@Path("val") String val,@Query("onDate") String onDate);
    @GET("ExRates/Currencies")
    Call<List<Currency>> getAllCurrencies();
    @GET("Metals")
    Call<List<MetalName>> getAllMetalNames();
}
