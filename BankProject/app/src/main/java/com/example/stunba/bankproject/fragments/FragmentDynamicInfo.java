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
    private Button draw;
    private String abb;
    private boolean isNotification=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_dynamics, container, false);
        Bundle bundle=getArguments();
        if(bundle.getString("abb")!=null){
            abb=bundle.getString("abb");
            isNotification=true;
        }
        if (savedInstanceState == null) {
            presenter = new DynamicPresenter(getContext(),this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
            restoreState(savedInstanceState);
        }
        initViews();
        presenter.loadInfo();
        if(isNotification){
            selectDate.setSelection(1);
            int position=presenter.getValueNumber(abb);
            if(position!=-1) {
                selectRate.setSelection(position);
            }
            loadDynamics("3",abb);
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
        outState.putString("startDate", startDate);
        outState.putString("endDate", endDate);
        outState.putString("rate", rate);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }
    private void restoreState(Bundle savedInstanceState) {
        startDate = savedInstanceState.getString("startDate");
        endDate = savedInstanceState.getString("endDate");
        rate=savedInstanceState.getString("rate");
    }

    protected void initViews() {
        chart = (BarChart) view.findViewById(R.id.chart);
        selectDate = (Spinner) view.findViewById(R.id.spinnerSelectDate);
        selectRate = (Spinner) view.findViewById(R.id.spinnerSelectRate);
        selectRate.setAdapter(presenter.getAdapter());
        draw = (Button) view.findViewById(R.id.buttonDraw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int year = Settings.CALENDAR.get(Calendar.YEAR);
                final int month = Settings.CALENDAR.get(Calendar.MONTH);
                final int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
                if (!selectDate.getSelectedItem().toString().equals(getResources().getString(R.string.select_none)) & !selectRate.getSelectedItem().toString().equals(getResources().getString(R.string.select_none))) {
                    endDate = Settings.getDate(year, month, day);
                    loadDynamics(selectDate.getSelectedItem().toString(),(String) selectRate.getSelectedItem());
                    if (startDate != null & endDate != null & rate != null) {
                        presenter.loadDynamics(rate, startDate, endDate);
                    }
                }
            }
        });
    }

    private void loadDynamics(String period,String rateAbb){
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

}