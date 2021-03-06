package com.example.stunba.bankproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stunba.bankproject.adapter.RecyclerViewAdapterMetal;
import com.example.stunba.bankproject.interfaces.MetalView;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.SimpleDividerDecoration;
import com.example.stunba.bankproject.presenters.ipresenters.IMetalScreen;
import com.example.stunba.bankproject.presenters.MetalScreenPresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class RecyclerFragmentMetal extends Fragment implements MetalView {
    private RecyclerView mRecyclerView;
    private IMetalScreen presenter;

    private RecyclerViewAdapterMetal recyclerViewAdapterMetal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        if (savedInstanceState == null) {
            presenter = new MetalScreenPresenter(getContext(), this);
        } else {
            presenter = (IMetalScreen) PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setView(this);
        }
        initView(view);
        presenter.loadInfo();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerViewAdapterMetal = new RecyclerViewAdapterMetal(getContext());
        mRecyclerView.setAdapter(recyclerViewAdapterMetal);
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(getContext()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
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

    @Override
    public void setDataForAdapter(List<ActualAllIngot> load, Map<Integer, MetalName> data) {
        recyclerViewAdapterMetal.setActualData(load);
        recyclerViewAdapterMetal.setNames(data);
        recyclerViewAdapterMetal.notifyDataSetChanged();
    }
}
