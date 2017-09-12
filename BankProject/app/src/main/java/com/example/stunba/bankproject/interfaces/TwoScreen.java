package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.Currency;
import com.example.stunba.bankproject.source.entities.DynamicPeriod;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public interface TwoScreen {
    interface DynamicView {
       void showDynamicInfo(List<DynamicPeriod> dynamicPeriods);
    }
}
