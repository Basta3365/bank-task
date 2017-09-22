package com.example.stunba.bankproject.presenters.ipresenters;

import android.support.v7.widget.RecyclerView;

import com.example.stunba.bankproject.interfaces.MetalView;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IMetalScreen extends BaseInterface {
    MetalView getView();

    void setView(MetalView view);

    void loadInfo();


    RecyclerView.Adapter getRecyclerViewAdapterMetal();
}