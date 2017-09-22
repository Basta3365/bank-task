package com.example.stunba.bankproject.presenters.ipresenters;

import com.example.stunba.bankproject.interfaces.MainView;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface ICurrentExchangeRate extends BaseInterface {
    void loadInfo(final List<String> list);

    MainView getView();

    void setView(MainView view);
}
