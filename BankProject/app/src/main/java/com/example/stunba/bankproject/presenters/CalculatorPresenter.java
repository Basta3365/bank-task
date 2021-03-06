package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.CalculatorView;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.presenters.ipresenters.ICalculator;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/7/2017.
 */

public class CalculatorPresenter implements ICalculator {
    private Repository repository;
    private CalculatorView calculatorView;
    private Map<String, Integer> currency;
    private ArrayList<String> strings;
    private ArrayAdapter<String> adapterFirst;
    private ArrayAdapter<String> adapterSecond;
    private List<String> names;

    public void setView(CalculatorView calculatorView) {
        this.calculatorView = calculatorView;
    }

    public ArrayAdapter<String> getAdapterFirst() {
        return adapterFirst;
    }

    public ArrayAdapter<String> getAdapterSecond() {
        return adapterSecond;
    }


    public CalculatorPresenter(Context context, CalculatorView view) {
        repository = Repository.getInstance(context);
        calculatorView = view;
        names = new ArrayList<>();
        strings = new ArrayList<>();
        currency = new HashMap<>();
        strings.add("BYR");
        adapterFirst = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, new ArrayList<String>());
        adapterFirst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSecond = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, strings);
        adapterSecond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void loadInfo() {
        if (names.size() > 0) {
            adapterFirst.clear();
            adapterFirst.addAll(names);
            adapterFirst.notifyDataSetChanged();
        } else {
            repository.getAllCurrencies(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    if (o != null) {
                        for (Currency cur : o) {
                            currency.put(cur.getCurAbbreviation(), cur.getCurID());
                            names.add(cur.getCurAbbreviation());
                        }
                        adapterFirst.clear();
                        adapterFirst.addAll(names);
                        adapterFirst.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void getRate(String abbFrom, String abbTo, double count) {
        repository.getRateCalculator(abbFrom, abbTo, count, new OnTaskCompleted.LoadComplete() {
            @Override
            public void onLoadComplete(double o) {
                if (o != -1) {
                    if (calculatorView != null) {
                        calculatorView.showChangeResults(o);
                    }
                } else {
                    if (calculatorView != null) {
                        calculatorView.showError("No information");
                    }
                }
            }
        });
    }

}
