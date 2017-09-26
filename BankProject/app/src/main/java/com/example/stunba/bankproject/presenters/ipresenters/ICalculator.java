package com.example.stunba.bankproject.presenters.ipresenters;

import android.widget.ArrayAdapter;

import com.example.stunba.bankproject.interfaces.CalculatorView;


/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface ICalculator extends BaseInterface {
    void loadInfo();

    void getRate(String abbFrom, String abbTo, double count);


    void setView(CalculatorView calculatorView);


    ArrayAdapter<String> getAdapterFirst();

    ArrayAdapter<String> getAdapterSecond();
}
