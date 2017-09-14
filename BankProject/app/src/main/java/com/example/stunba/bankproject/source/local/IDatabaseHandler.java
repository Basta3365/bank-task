package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandler {
    void addRate(ActualRate rate);

    void getAllRates(OnTaskCompleted.MainPresenterComplete mainPresenterComplete);

    int updateRate(ActualRate rate);

    void deleteRate(ActualRate rate);

    void deleteAll();
}