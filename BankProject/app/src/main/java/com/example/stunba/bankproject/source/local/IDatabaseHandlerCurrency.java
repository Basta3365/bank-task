package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.Currency;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandlerCurrency {
    void addCurrency(Currency currency);
    void getCurrency(int id, OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency);
    void getAllCurrencies(OnTaskCompleted.DynamicPresenterCompleteCurrency dynamicPresenterCompleteCurrency);
    int getCurrenciesCount();
    int updateCurrency(Currency currency);
    void deleteCurrency(Currency currency);
    void deleteAll();
}