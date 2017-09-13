package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.TwoScreen;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.example.stunba.bankproject.source.remote.BankApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;


/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public class DynamicInfoPresenter extends BasePresenter<TwoScreen.DynamicView> {
    private Repository repository;
    private TwoScreen.DynamicView dynamicView;
    private BankApi bankApi;
    private Map<String, Integer> currency;
    private ArrayAdapter<String> adapter = null;

    public void setDynamicView(TwoScreen.DynamicView dynamicView) {
        this.dynamicView = dynamicView;
    }

    public DynamicInfoPresenter(Context context, TwoScreen.DynamicView view) {
        repository = Repository.getInstance(context);
        dynamicView = view;
        currency = new HashMap<>();
        bankApi = new BankApi();
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

    public void loadDynamics(String val, String startDate, String endDate) {
        bankApi.getDynamicsPeriod(String.valueOf(currency.get(val)), startDate, endDate, new OnTaskCompleted.DynamicPresenterCompleteDynamic() {
            @Override
            public void onDynamicLoad(List<DynamicPeriod> o) {
                if (o != null) {
                    dynamicView.showDynamicInfo(o);
                }else {
                    dynamicView.showDynamicInfo(null);
                }
            }
        });
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public int getValueNumber(String abb) {
        int number = -1;
        List<String> list = new ArrayList<>(currency.keySet());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(abb)) {
                number = i;
                break;
            }
        }
        return number;

    }

}
