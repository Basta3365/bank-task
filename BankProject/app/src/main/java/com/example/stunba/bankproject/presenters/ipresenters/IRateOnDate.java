package com.example.stunba.bankproject.presenters.ipresenters;

import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.RateOnDateView;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IRateOnDate extends BaseInterface {
    void loadInfo();

    ArrayAdapter<String> getAdapter();

    void actualRate(final String val, String date);

    RateOnDateView getView();

    void setView(RateOnDateView view);
}
