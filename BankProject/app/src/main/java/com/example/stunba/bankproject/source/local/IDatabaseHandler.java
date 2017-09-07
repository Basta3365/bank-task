package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandler {
    void addRate(ActualRate rate);
    void getRate(int id, OnTaskCompleted.MainPresenterComplete mainPresenterComplete);
    void getAllRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete);
    int getRatesCount();
    int updateRate(ActualRate rate);
    void deleteRate(ActualRate rate);
    void deleteAll();
}