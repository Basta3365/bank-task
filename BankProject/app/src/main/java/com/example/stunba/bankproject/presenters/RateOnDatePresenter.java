package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.RateOnDateView;
import com.example.stunba.bankproject.presenters.ipresenters.IRateOnDate;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/1/2017.
 */

public class RateOnDatePresenter implements IRateOnDate {
    private Repository repository;
    private Map<String, Integer> currency;
    private List<String> names;
    private RateOnDateView calculateView;
    private Context context;

    public RateOnDatePresenter(Context context, RateOnDateView v) {
        repository = Repository.getInstance(context);
        calculateView = v;
        this.context = context;
        currency = new HashMap<>();
        names = new ArrayList<>();
    }

    public void loadInfo() {
        if (names.size() > 0) {
            if (calculateView != null)
                calculateView.setDataForAdapter(names);

        } else {
            repository.getAllCurrencies(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    if (o != null) {
                        for (Currency cur : o) {
                            currency.put(cur.getCurAbbreviation(), cur.getCurID());
                            names.add(cur.getCurAbbreviation());
                        }
                        if (calculateView != null)
                            calculateView.setDataForAdapter(names);
                    }
                }
            });
        }
    }

    public void actualRate(final String val, String date) {
        if (internetAvailable()) {
            repository.getRateByDate(String.valueOf(currency.get(val)), date, new OnTaskCompleted.LoadActualRate() {
                @Override
                public void onLoadRate(ActualRate o) {
                    if (calculateView != null) {
                        calculateView.showActualRate(o);
                    }
                }
            });
        } else {
            if (calculateView != null) {
                calculateView.showError("Internet not available");
            }
        }
    }

    private Boolean internetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void setView(RateOnDateView calculateView) {
        this.calculateView = calculateView;
    }
}
