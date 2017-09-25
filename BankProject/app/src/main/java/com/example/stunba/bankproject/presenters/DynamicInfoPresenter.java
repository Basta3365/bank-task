package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.DynamicView;
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
    private DynamicView dynamicView;
    private ArrayAdapter<String> adapter = null;
    private Context context;


    public DynamicInfoPresenter(Context context, DynamicView view) {
        repository = Repository.getInstance(context);
        dynamicView = view;
        this.context = context;
        currency = new HashMap<>();
        names = new ArrayList<>();
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

    public void loadDynamics(String val, String startDate, String endDate) {
        if (internetAvailable()) {
            bankApi.getDynamicsPeriod(String.valueOf(currency.get(val)), startDate, endDate, new OnTaskCompleted.DynamicPresenterCompleteDynamic() {
                @Override
                public void onDynamicLoad(List<DynamicPeriod> o) {
                    if (o != null && o.size() != 0) {
                        if (getView() != null) {
                            getView().showDynamicInfo(o);
                        }
                    } else {
                        if (getView() != null) {
                            getView().showError("No information");
                        }
                    }
                }
            });
        } else {
            if (getView() != null) {
                getView().showError("Internet not available");
            }
        }
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    @Override
    public DynamicView getView() {
        return dynamicView;
    }

    @Override
    public void setView(DynamicView view) {
        dynamicView = view;
    }

    public int getValueNumber(String abb) {
        int number = -1;
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(abb)) {
                number = i;
                break;
            }
        }
        return number;

    }

    private Boolean internetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
