package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.Map;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */

public interface MainView {
    void showLoadInfo(Map<String, ActualRate> map);
}
