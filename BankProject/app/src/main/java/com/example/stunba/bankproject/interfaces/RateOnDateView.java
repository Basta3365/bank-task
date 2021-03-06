package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/1/2017.
 */

public interface RateOnDateView {
    void showActualRate(ActualRate rate);

    void showError(String s);

    void setDataForAdapter(List<String> data);
}
