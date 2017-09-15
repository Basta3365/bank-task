package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.DynamicPeriod;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public interface DynamicInfoScreen {
    interface DynamicView {
        void showDynamicInfo(List<DynamicPeriod> dynamicPeriods);
    }
}
