package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/24/2017.
 */

public interface OnTaskCompleted {
    interface LoadAllCurrencies {
        void onAllCurrencyLoad(List<Currency> o);
    }

    interface DynamicPresenterCompleteDynamic {
        void onDynamicLoad(List<DynamicPeriod> o);
    }

    interface MetalLoadAll {
        void onAllIngot(List<ActualAllIngot> o);
    }

    interface MetalLoad {
        void onIngot(ActualAllIngot o);
    }

    interface MetalNamesLoadAll {
        void onAllNames(List<MetalName> o);
    }

    interface MetalNamesLoad {
        void onNames(MetalName o);
    }

    interface LoadAllActualRate {
        void onLoadAllRate(List<ActualRate> o);
    }

    interface LoadActualRate {
        void onLoadRate(ActualRate o);
    }

    interface LoadFavoriteMap {
        void onLoadMap(Map<String, Double> changes);
    }

    interface LoadComplete {
        void onLoadComplete(double o);
    }

    interface DeleteFavorite {
        void onDeleteItem(ActualRate actualRate);
    }

    interface LoadSuccessfully {
        void onLoadSuccess(boolean o);
    }
}
