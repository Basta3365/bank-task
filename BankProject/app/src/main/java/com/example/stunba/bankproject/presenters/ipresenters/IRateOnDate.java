package com.example.stunba.bankproject.presenters.ipresenters;


import com.example.stunba.bankproject.interfaces.RateOnDateView;


/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IRateOnDate extends BaseInterface {
    void loadInfo();

    void actualRate(final String val, String date);

    void setView(RateOnDateView rateOnDateView);
}
