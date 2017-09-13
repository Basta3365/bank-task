package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.Currency;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandlerCurrency {
    void addCurrency(Currency currency);

    void getCurrency(int id, OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency);

    void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency);

    int getCurrenciesCount();

    int updateCurrency(Currency currency);

    void deleteCurrency(int id);

    void deleteAll();
}
