package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.ActualRate;

/**
 * Created by Kseniya_Bastun on 9/1/2017.
 */

public interface TreeScreen {
    interface CalculateView{
        void showActualRate(ActualRate rate);
    }
}