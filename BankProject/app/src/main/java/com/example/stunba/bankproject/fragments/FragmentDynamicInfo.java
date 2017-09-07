package com.example.stunba.bankproject.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.stunba.bankproject.presenters.DynamicPresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.Settings;
import com.example.stunba.bankproject.TwoScreen;
import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public class FragmentDynamicInfo extends Fragment implements TwoScreen.DynamicView {
    private View view;
    private DynamicPresenter presenter;
    private BarChart chart;
    private Spinner selectRate;
    private Spinner selectDate;
    private String startDate;
    private String endDate;
    private String rate;
    private String value;
    private Button draw;
    private Map<String,String> currencyIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_dynamics, container, false);
        if (savedInstanceState == null) {
            presenter = new DynamicPresenter(getContext(),this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        initViews();
        presenter.loadInfo();
        return view;
    }

    @Override
    public void showDynamicInfo(List<DynamicPeriod> dynamicPeriods) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dynamicPeriods.size(); i++) {
            entries.add(new BarEntry(i, (float) dynamicPeriods.get(i).getCurOfficialRate()));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Rate");
        dataSet.setValueTextColor(Color.TRANSPARENT);
        BarData data = new BarData(dataSet);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        chart.setData(data);
        chart.animateY(3000);
        chart.invalidate();


    }

    @Override
    public void showAllCurrencies(List<Currency> currencies) {
        for (Currency cur: currencies) {
            currencyIndex.put(cur.getCurAbbreviation(),String.valueOf(cur.getCurID()));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>(currencyIndex.keySet()));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectRate.setAdapter(spinnerAdapter);
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

    protected void initViews() {
        chart = (BarChart) view.findViewById(R.id.chart);
        selectDate = (Spinner) view.findViewById(R.id.spinnerSelectDate);
        selectRate = (Spinner) view.findViewById(R.id.spinnerSelectRate);
        draw = (Button) view.findViewById(R.id.buttonDraw);
        currencyIndex=new HashMap<>();
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int year = Settings.CALENDAR.get(Calendar.YEAR);
                final int month = Settings.CALENDAR.get(Calendar.MONTH);
                final int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
                if (!selectDate.getSelectedItem().toString().equals(getResources().getString(R.string.select_none)) & !selectRate.getSelectedItem().toString().equals(getResources().getString(R.string.select_none))) {
                    endDate = Settings.getDate(year, month, day);
                    Settings.CALENDAR.add(Calendar.MONTH, -Integer.valueOf(selectDate.getSelectedItem().toString()));
                    int yearEnd = Settings.CALENDAR.get(Calendar.YEAR);
                    int monthEnd = Settings.CALENDAR.get(Calendar.MONTH);
                    int dayEnd = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
                    startDate = Settings.getDate(yearEnd, monthEnd, dayEnd);
                    Settings.CALENDAR.add(Calendar.MONTH, +Integer.valueOf(selectDate.getSelectedItem().toString()));
                    rate = (String) selectRate.getSelectedItem();
                    value = currencyIndex.get(rate);
                    if (startDate != null & endDate != null & value != null) {
                        presenter.loadDynamics(value, startDate, endDate);
                    }
                }
            }
        });
    }

//    public String getDate(int year, int month, int dayOfMonth) {
//        String sMonth = String.valueOf(month + 1);
//        String sDay = String.valueOf(dayOfMonth);
//        return year + "-" + sMonth + "-" + sDay;
//
//    }

    protected String getValue(String text) {
        String value = null;
        switch (text) {
            case "USD": {
                value = "145";
                break;
            }
            case "EUR": {
                value = "292";
                break;
            }
            case "RUB": {
                value = "298";
                break;
            }
        }
        return value;
    }


}
