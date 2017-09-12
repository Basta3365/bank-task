package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.TreeScreen;
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

public class RateOnDatePresenter extends BasePresenter<TreeScreen.CalculateView> {
    private Repository repository;
    private TreeScreen.CalculateView calculateView;
    private Map<String, Integer> currency;
    private ArrayAdapter<String> adapter = null;

    public void setCalculateView(TreeScreen.CalculateView calculateView) {
        this.calculateView = calculateView;
    }

    public RateOnDatePresenter(Context context, TreeScreen.CalculateView v) {
        repository = Repository.getInstance(context);
        calculateView = v;
        currency = new HashMap<>();
        adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void loadInfo() {
        if (currency.size() > 0) {
            adapter.clear();
            adapter.addAll(new ArrayList<>(currency.keySet()));
            adapter.notifyDataSetChanged();
        }
        repository.getAllCurrencies(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
            @Override
            public void onAllCurrencyLoad(Object o) {
                if (o != null) {
                    for (Currency cur : (List<Currency>) o) {
                        currency.put(cur.getCurAbbreviation(), cur.getCurID());
                    }
                    adapter.clear();
                    adapter.addAll(new ArrayList<>(currency.keySet()));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void actualRate(final String val, String date) {
        repository.getRateByDate(String.valueOf(currency.get(val)), date, new OnTaskCompleted.CalculatePresenterComplete() {
            @Override
            public void onLoadRateByDate(Object o) {
                calculateView.showActualRate((ActualRate) o);
            }
        });
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }
}
