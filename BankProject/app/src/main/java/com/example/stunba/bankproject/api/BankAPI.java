package com.example.stunba.bankproject.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by stunba on 8/24/17.
 */

public interface BankAPI {
    @GET("ExRates/Rates/{val}")
    Call<ActualRate> getActualRAte(@Path("val") String val);
}
