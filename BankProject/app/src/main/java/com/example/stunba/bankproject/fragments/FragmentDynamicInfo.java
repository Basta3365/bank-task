package com.example.stunba.bankproject.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stunba.bankproject.presenters.ipresenters.IDynamicInfo;
import com.example.stunba.bankproject.presenters.DynamicInfoPresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.Settings;
import com.example.stunba.bankproject.interfaces.DynamicView;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public class FragmentDynamicInfo extends Fragment implements DynamicView {
    private IDynamicInfo presenter;
    private BarChart chart;
    private Spinner selectRate;
    private Spinner selectDate;
    private String startDate;
    private String endDate;
    private String rate;
    private String abb;
    private String defaultPeriod = "3";
    private boolean isNotification = false;
    private boolean isDraw = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamics, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString("abb") != null) {
                abb = bundle.getString("abb");
                isNotification = true;
            }
        }
        if (savedInstanceState == null) {
            presenter = new DynamicInfoPresenter(getContext(), this);
        } else {
            presenter = (IDynamicInfo) PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setView(this);
            restoreState(savedInstanceState);
        }
        initViews(view);
        presenter.loadInfo();
        if (isNotification) {
            selectDate.setSelection(1);
            int position = presenter.getValueNumber(abb);
            if (position != -1) {
                selectRate.setSelection(position);
            }
            loadDynamics(defaultPeriod, abb);
        }
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
    public void showError(String error){
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.setView(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startDate", startDate);
        outState.putString("endDate", endDate);
        outState.putString("rate", rate);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState.getString("startDate") != null) {
            startDate = savedInstanceState.getString("startDate");
        }
        if (savedInstanceState.getString("endDate") != null) {
            endDate = savedInstanceState.getString("endDate");
        }
        if (savedInstanceState.getString("endDate") != null) {
            rate = savedInstanceState.getString("rate");
        }
        if (startDate != null & endDate != null & rate != null) {
            presenter.loadDynamics(rate, startDate, endDate);
        }
    }

    protected void initViews(View view) {
        chart = (BarChart) view.findViewById(R.id.chart);
        selectDate = (Spinner) view.findViewById(R.id.spinnerSelectDate);
        selectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDraw) {
                    drawDynamics();
                } else {
                    isDraw = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectRate = (Spinner) view.findViewById(R.id.spinnerSelectRate);
        selectRate.setAdapter(presenter.getAdapter());
        selectRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDraw) {
                    drawDynamics();
                } else {
                    isDraw = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadDynamics(String period, String rateAbb) {
        rate = rateAbb;
        final int year = Settings.CALENDAR.get(Calendar.YEAR);
        final int month = Settings.CALENDAR.get(Calendar.MONTH);
        final int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
        endDate = Settings.getDate(year, month, day);
        Settings.CALENDAR.add(Calendar.MONTH, -Integer.valueOf(period));
        int yearEnd = Settings.CALENDAR.get(Calendar.YEAR);
        int monthEnd = Settings.CALENDAR.get(Calendar.MONTH);
        int dayEnd = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
        startDate = Settings.getDate(yearEnd, monthEnd, dayEnd);
        Settings.CALENDAR.add(Calendar.MONTH, +Integer.valueOf(period));
        if (startDate != null & endDate != null & rateAbb != null) {
            presenter.loadDynamics(rateAbb, startDate, endDate);
        }
    }


    private void drawDynamics() {
        if (!selectDate.getSelectedItem().toString().equals(getResources().getString(R.string.select_none)) & !selectRate.getSelectedItem().toString().equals(getResources().getString(R.string.select_none))) {
            loadDynamics(String.valueOf(selectDate.getSelectedItem().toString().toCharArray()[0]), (String) selectRate.getSelectedItem());
        }
    }
}
