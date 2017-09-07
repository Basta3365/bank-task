package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.OneScreen;
import com.example.stunba.bankproject.presenters.BasePresenter;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.remote.BankApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/23/2017.
 */

public class MainPresenter extends BasePresenter<List<String>,OneScreen.MainView> {
    private Repository repository;
    private OneScreen.MainView mainView;
    private BankApi bankApi;
    public MainPresenter(Context context, OneScreen.MainView view){
        repository=Repository.getInstance(context);
        mainView=view;
        bankApi=new BankApi();
    }


    public void loadInfo(final List<String> list) {
        final Map<String,ActualRate> actualRateMap=new HashMap<>();
        for (String str: list) {
            repository.getRateByAbb(str, new OnTaskCompleted.MainPresenterComplete() {
                @Override
                public void onLoadRate(Object o) {
                    actualRateMap.put(((ActualRate)o).getCurAbbreviation(),(ActualRate) o);
                    if(actualRateMap.size()==list.size()){
                        mainView.showLoadInfo(actualRateMap);
                    }
                }
            });
        }
    }

}
