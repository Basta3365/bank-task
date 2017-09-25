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
    private ArrayAdapter<String> adapter = null;
    private List<String> names;
    private RateOnDateView calculateView;
    private Context context;

    public RateOnDatePresenter(Context context, RateOnDateView v) {
        repository = Repository.getInstance(context);
        calculateView = v;
        this.context = context;
        currency = new HashMap<>();
        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void loadInfo() {
        if (names.size() > 0) {
            adapter.clear();
            adapter.addAll(names);
            adapter.notifyDataSetChanged();
        } else {
            repository.getAllCurrencies(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    if (o != null) {
                        for (Currency cur : o) {
                            currency.put(cur.getCurAbbreviation(), cur.getCurID());
                            names.add(cur.getCurAbbreviation());
                        }
                        adapter.clear();
                        adapter.addAll(names);
                        adapter.notifyDataSetChanged();
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
                    if (getView() != null) {
                        getView().showActualRate(o);
                    }
                }
            });
        } else {
            getView().showError("Internet not available");
        }
    }

    @Override
    public RateOnDateView getView() {
        return calculateView;
    }

    @Override
    public void setView(RateOnDateView view) {
        calculateView = view;
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    private Boolean internetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
