package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.DynamicPeriod;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/24/2017.
 */

public interface OnTaskCompleted {
    interface DynamicPresenterCompleteCurrency {
        void onAllCurrencyLoad(Object o);
    }
    interface DynamicPresenterCompleteDynamic{
        void onDynamicLoad(List<DynamicPeriod> o);
    }
    interface MainPresenterComplete{
        void onLoadRate(Object o);
    }
    interface CalculatePresenterComplete{
        void onLoadRateByDate(Object o);
    }

    interface FavoritePresenter {
        void onAllFavorites(Object o);
    }
    interface LoadComplete {
        void onLoadComplete(Object o);
    }
}
