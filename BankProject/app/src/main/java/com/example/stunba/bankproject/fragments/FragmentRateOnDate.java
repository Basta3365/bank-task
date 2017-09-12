package com.example.stunba.bankproject.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stunba.bankproject.Settings;
import com.example.stunba.bankproject.presenters.RateOnDatePresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.interfaces.TreeScreen;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.Calendar;

/**
 * Created by Kseniya_Bastun on 9/1/2017.
 */

public class FragmentRateOnDate extends Fragment implements TreeScreen.CalculateView {
    private View view;
    private RateOnDatePresenter presenter;
    private Spinner selectRate;
    private TextView selectDate;
    private TextView actualRateTextScreen;
    private String startDate;
    private String value;
    private String actualRateText;
    private DatePickerDialog dialogDepartureDate;
    private boolean isCalculate = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_rate_on_date, container, false);
        if (savedInstanceState == null) {
            presenter = new RateOnDatePresenter(getContext(), this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setCalculateView(this);
            restoreState(savedInstanceState);
        }
        initViews();
        presenter.loadInfo();
        return view;
    }

    private void restoreState(Bundle savedInstanceState) {
        startDate = savedInstanceState.getString("date");
        actualRateText = savedInstanceState.getString("actual_rate");
    }

    private void initViews() {
        selectDate = (TextView) view.findViewById(R.id.textViewSelectDate);
        if (startDate != null) {
            selectDate.setText(startDate);
        }
        selectRate = (Spinner) view.findViewById(R.id.spinnerSelectRateCalculate);
        selectRate.setAdapter(presenter.getAdapter());
        selectRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isCalculate && startDate != null) {
                    value = (String) selectRate.getSelectedItem();
                    if(internetAvailable()) {
                        presenter.actualRate(value, startDate);
                    }else {
                        Toast.makeText(getContext(), "Internet not available", Toast.LENGTH_SHORT);
                    }
                } else {
                    isCalculate = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        actualRateTextScreen = (TextView) view.findViewById(R.id.textViewActualRateScreen);
        if (actualRateText != null) {
            actualRateTextScreen.setText(actualRateText);
        }
        final int year = Settings.CALENDAR.get(Calendar.YEAR);
        final int month = Settings.CALENDAR.get(Calendar.MONTH);
        final int day = Settings.CALENDAR.get(Calendar.DAY_OF_MONTH);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listenerDeparture = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate = Settings.getDate(year, month, dayOfMonth);
                        selectDate.setText(startDate);
                        if (isCalculate) {
                            value = (String) selectRate.getSelectedItem();
                            if(internetAvailable()) {
                                presenter.actualRate(value, startDate);
                            }else {
                                Toast.makeText(getContext(), "Internet not available", Toast.LENGTH_SHORT);
                            }
                        } else {
                            isCalculate = true;
                        }
                    }
                };
                dialogDepartureDate = new DatePickerDialog(getContext(), listenerDeparture, year, month, day);
                dialogDepartureDate.show();

            }

        });

    }

    @Override
    public void showActualRate(ActualRate rate) {
        if (rate != null) {
            actualRateTextScreen.setText(String.valueOf(rate.getCurOfficialRate()));
        } else {
            actualRateTextScreen.setText(FragmentRateOnDate.this.getString(R.string.wrong_input));
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
        outState.putString("date", startDate);
        outState.putString("actual_rate", actualRateTextScreen.getText().toString());
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    public Boolean internetAvailable() {
        ConnectivityManager connectManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean internetAvailable = (connectManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        return internetAvailable;
    }


}
