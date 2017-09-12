package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.OneScreen;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.remote.BankApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/23/2017.
 */

public class CurrentExchangeRatePresenter extends BasePresenter<OneScreen.MainView> {
    private Repository repository;
    private OneScreen.MainView mainView;

    public CurrentExchangeRatePresenter(Context context, OneScreen.MainView view) {
        repository = Repository.getInstance(context);
        mainView = view;
    }


    public void loadInfo(final List<String> list) {
        final Map<String, ActualRate> actualRateMap = new HashMap<>();
        for (String str : list) {
            repository.getRateByAbb(str, new OnTaskCompleted.MainPresenterComplete() {
                @Override
                public void onLoadRate(Object o) {
                    actualRateMap.put(((ActualRate) o).getCurAbbreviation(), (ActualRate) o);
                    if (actualRateMap.size() == list.size()) {
                        mainView.showLoadInfo(actualRateMap);
                    }
                }
            });
        }
    }

}
