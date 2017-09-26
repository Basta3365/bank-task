package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/8/2017.
 */

public interface FavoriteView {
    void showError(String str);

    void setDataForAdapter(List<ActualRate> data);
}
