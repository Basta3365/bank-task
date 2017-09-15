package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.DynamicInfoScreen;
import com.example.stunba.bankproject.presenters.ipresenters.IDynamicInfo;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.example.stunba.bankproject.source.remote.BankApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public class DynamicInfoPresenter implements IDynamicInfo {
    private Repository repository;
    private BankApi bankApi;
    private Map<String, Integer> currency;
    private List<String> names;
    private DynamicInfoScreen.DynamicView dynamicView;
    private ArrayAdapter<String> adapter = null;


    public DynamicInfoPresenter(Context context, DynamicInfoScreen.DynamicView view) {
        repository = Repository.getInstance(context);
        dynamicView=view;
        currency = new HashMap<>();
        names=new ArrayList<>();
        bankApi = new BankApi();
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

    public void loadDynamics(String val, String startDate, String endDate) {
        bankApi.getDynamicsPeriod(String.valueOf(currency.get(val)), startDate, endDate, new OnTaskCompleted.DynamicPresenterCompleteDynamic() {
            @Override
            public void onDynamicLoad(List<DynamicPeriod> o) {
                if (o != null) {
                    getView().showDynamicInfo(o);
                }else {
                    getView().showDynamicInfo(null);
                }
            }
        });
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    @Override
    public DynamicInfoScreen.DynamicView getView() {
        return dynamicView;
    }

    @Override
    public void setView(DynamicInfoScreen.DynamicView view) {
        dynamicView=view;
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
