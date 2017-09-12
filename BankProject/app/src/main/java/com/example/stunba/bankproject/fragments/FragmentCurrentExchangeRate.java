package com.example.stunba.bankproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stunba.bankproject.presenters.CurrentExchangeRatePresenter;
import com.example.stunba.bankproject.interfaces.OneScreen;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/24/2017.
 */

public class FragmentCurrentExchangeRate extends Fragment implements OneScreen.MainView {
    private View view;
    private TextView usdRate;
    private TextView rubRate;
    private TextView eurRate;
    private CurrentExchangeRatePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_current_exchange_rate, container, false);
        if (savedInstanceState == null) {
            presenter = new CurrentExchangeRatePresenter(getContext(), this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        initViews();
        List<String> listCurrency = new ArrayList<>(3);
        listCurrency.add("USD");
        listCurrency.add("EUR");
        listCurrency.add("RUB");
        presenter.loadInfo(listCurrency);
        return view;
    }

    private void initViews() {
        usdRate = (TextView) view.findViewById(R.id.textViewActualUSD);
        eurRate = (TextView) view.findViewById(R.id.textViewActualEUR);
        rubRate = (TextView) view.findViewById(R.id.textViewActualRUB);
    }

    @Override
    public void showLoadInfo(Map<String, ActualRate> map) {
        for (Map.Entry<String, ActualRate> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "USD": {
                    usdRate.setText(String.valueOf(entry.getValue().getCurOfficialRate()));
                    break;
                }
                case "EUR": {
                    eurRate.setText(String.valueOf(entry.getValue().getCurOfficialRate()));
                    break;
                }
                case "RUB": {
                    rubRate.setText(String.valueOf(entry.getValue().getCurOfficialRate()));
                    break;
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

}
