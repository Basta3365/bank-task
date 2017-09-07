package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.TwoScreen;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.example.stunba.bankproject.source.remote.BankApi;

import java.util.List;


/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public class DynamicPresenter extends BasePresenter<List<String>,TwoScreen.DynamicView> {
    private Repository repository;
    private TwoScreen.DynamicView dynamicView;
    private BankApi bankApi;
    public DynamicPresenter(Context context, TwoScreen.DynamicView view){
        repository=Repository.getInstance(context);
        dynamicView=view;
        bankApi=new BankApi();
    }
    public void loadInfo() {
        repository.getAllCurrencies(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
            @Override
            public void onAllCurrencyLoad(Object o) {
                if(o!=null){
                    dynamicView.showAllCurrencies((List<Currency>) o);
                }
            }
        });
    }

    public void loadDynamics(String val, String startDate, String endDate) {
       bankApi.getDynamicsPeriod(val,startDate,endDate, new OnTaskCompleted.DynamicPresenterCompleteDynamic() {
           @Override
           public void onDynamicLoad(List<DynamicPeriod> o) {
               if(o!=null){
                   dynamicView.showDynamicInfo(o);
               }
           }
       });
    }

}
