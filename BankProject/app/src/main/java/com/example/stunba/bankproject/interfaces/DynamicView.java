package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.DynamicPeriod;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public interface DynamicView {
    void showDynamicInfo(List<DynamicPeriod> dynamicPeriods);

    void showError(String error);

    void setDatForAdapter(List<String> data);
}
