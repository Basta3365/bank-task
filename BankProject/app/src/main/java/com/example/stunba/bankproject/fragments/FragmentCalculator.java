package com.example.stunba.bankproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stunba.bankproject.interfaces.CalculatorView;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.presenters.CalculatorPresenter;
import com.example.stunba.bankproject.presenters.ipresenters.ICalculator;
import com.example.stunba.bankproject.presenters.PresenterManager;

/**
 * Created by Kseniya_Bastun on 9/7/2017.
 */

public class FragmentCalculator extends Fragment implements CalculatorView {
    private ICalculator presenter;
    private Spinner selectFirstRate;
    private Spinner selectSecondRate;
    private TextView firstText;
    private TextView secondText;
    private ImageButton change;
    private boolean isBYR = false;
    private boolean isSelect = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        if (savedInstanceState == null) {
            presenter = new CalculatorPresenter(getContext(), this);
        } else {
            presenter = (ICalculator) PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setView(this);

        }
        initViews(view);
        presenter.loadInfo();
        return view;
    }

    private void initViews(View view) {
        selectFirstRate = (Spinner) view.findViewById(R.id.spinnerFirst);
        selectFirstRate.setAdapter(presenter.getAdapterFirst());
        selectFirstRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSelect & firstText.getText().length() > 0) {
                    presenter.getRate(selectFirstRate.getSelectedItem().toString(), selectSecondRate.getSelectedItem().toString(), Double.parseDouble(firstText.getText().toString()));
                } else {
                    isSelect = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectSecondRate = (Spinner) view.findViewById(R.id.spinnerSecond);
        selectSecondRate.setAdapter(presenter.getAdapterSecond());
        selectSecondRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSelect & firstText.getText().length() > 0) {
                    presenter.getRate(selectFirstRate.getSelectedItem().toString(), selectSecondRate.getSelectedItem().toString(), Double.parseDouble(firstText.getText().toString()));
                } else {
                    isSelect = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        firstText = (TextView) view.findViewById(R.id.editTextFirst);
        firstText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    presenter.getRate(selectFirstRate.getSelectedItem().toString(), selectSecondRate.getSelectedItem().toString(), Double.parseDouble(firstText.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        secondText = (TextView) view.findViewById(R.id.editTextSecond);
        change = (ImageButton) view.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBYR) {
                    int select = selectFirstRate.getSelectedItemPosition();
                    selectFirstRate.setAdapter(presenter.getAdapterSecond());
                    selectSecondRate.setAdapter(presenter.getAdapterFirst());
                    selectSecondRate.setSelection(select);
                    firstText.setText(secondText.getText());
                    isBYR = true;
                } else {
                    int select = selectSecondRate.getSelectedItemPosition();
                    selectFirstRate.setAdapter(presenter.getAdapterFirst());
                    selectFirstRate.setSelection(select);
                    selectSecondRate.setAdapter(presenter.getAdapterSecond());
                    firstText.setText(secondText.getText());
                    isBYR = false;
                }
            }
        });
    }


    @Override
    public void showChangeResults(double o) {
        secondText.setText(String.valueOf(o));
    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.setView(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
