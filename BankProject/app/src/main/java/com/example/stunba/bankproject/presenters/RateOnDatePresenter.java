package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.RateOnDateScreen;
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
    private RateOnDateScreen.RateOnDateView calculateView;

    public RateOnDatePresenter(Context context, RateOnDateScreen.RateOnDateView v) {
        repository = Repository.getInstance(context);
        calculateView=v;
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
        }else {
            repository.getAllCurrencies(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    if (o != null) {
                        for (Currency cur :  o) {
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
        repository.getRateByDate(String.valueOf(currency.get(val)), date, new OnTaskCompleted.LoadActualRate() {
            @Override
            public void onLoadRate(ActualRate o) {
                getView().showActualRate(o);
            }
        });
    }

    @Override
    public RateOnDateScreen.RateOnDateView getView() {
        return calculateView;
    }

    @Override
    public void setView(RateOnDateScreen.RateOnDateView view) {
        calculateView=view;
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }
}
