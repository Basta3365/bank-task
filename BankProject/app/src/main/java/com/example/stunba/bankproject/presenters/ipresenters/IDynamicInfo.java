package com.example.stunba.bankproject.presenters.ipresenters;


import com.example.stunba.bankproject.interfaces.DynamicView;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IDynamicInfo extends BaseInterface {
    void loadInfo();

    int getValueNumber(String abb);

    void loadDynamics(String val, String startDate, String endDate);

    void setView(DynamicView dynamicView);
}
