package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.interfaces.CurrentExchangeRateScreen;
import com.example.stunba.bankproject.presenters.ipresenters.ICurrentExchangeRate;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/23/2017.
 */

public class CurrentExchangeRatePresenter implements ICurrentExchangeRate {
    private Repository repository;
    private CurrentExchangeRateScreen.MainView view;

    public CurrentExchangeRateScreen.MainView getView() {
        return view;
    }

    public void setView(CurrentExchangeRateScreen.MainView view) {
        this.view = view;
    }

    public CurrentExchangeRatePresenter(Context context, CurrentExchangeRateScreen.MainView view) {
        repository = Repository.getInstance(context);
        this.view=view;
    }


    public void loadInfo(final List<String> list) {
        final Map<String, ActualRate> actualRateMap = new HashMap<>();
        for (String str : list) {
            repository.getRateByAbb(str, new OnTaskCompleted.LoadActualRate() {
                @Override
                public void onLoadRate(ActualRate o) {
                    actualRateMap.put( o.getCurAbbreviation(), o);
                    if (actualRateMap.size() == list.size()) {
                        view.showLoadInfo(actualRateMap);
                    }
                }
            });
        }
    }

}
