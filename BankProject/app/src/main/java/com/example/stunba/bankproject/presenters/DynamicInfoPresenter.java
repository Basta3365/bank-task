package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
    private Context context;


    public DynamicInfoPresenter(Context context, DynamicView view) {
        repository = Repository.getInstance(context);
        dynamicView = view;
        this.context = context;
        currency = new HashMap<>();
        names = new ArrayList<>();
        bankApi = new BankApi();

    }

    public void loadInfo() {
        if (names.size() > 0) {
            if (dynamicView != null) {
                dynamicView.setDatForAdapter(names);
            }
        } else {
            repository.getAllCurrencies(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    if (o != null) {
                        for (Currency cur : o) {
                            currency.put(cur.getCurAbbreviation(), cur.getCurID());
                            names.add(cur.getCurAbbreviation());
                        }
                        if (dynamicView != null) {
                            dynamicView.setDatForAdapter(names);
                        }
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
                    if (o != null) {
                        if (o.size() != 0) {
                            if (dynamicView != null) {
                                dynamicView.showDynamicInfo(o);
                            }
                        } else {
                            if (dynamicView != null) {
                                dynamicView.showError("No information");
                            }
                        }
                    }
                }
            });
        } else {
            if (dynamicView != null) {
                dynamicView.showError("Internet not available");
            }
        }
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

    public void setView(DynamicView dynamicView) {
        this.dynamicView = dynamicView;
    }
}
