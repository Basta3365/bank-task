package com.example.stunba.bankproject.presenters.ipresenters;

import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.DynamicView;


/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IDynamicInfo extends BaseInterface {
    void loadInfo();

    int getValueNumber(String abb);

    void loadDynamics(String val, String startDate, String endDate);

    ArrayAdapter<String> getAdapter();

    DynamicView getView();

    void setView(DynamicView view);
}
