package com.example.stunba.bankproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stunba.bankproject.FourScreen;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterMetal;
import com.example.stunba.bankproject.SimpleDividerDecoration;
import com.example.stunba.bankproject.presenters.MetalScreenPresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class RecyclerFragmentMetal extends Fragment implements FourScreen.MetalView {
    private RecyclerView mRecyclerView;
    private View view;
    private MetalScreenPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.recycler_view, container, false);
        }
        if (savedInstanceState == null) {
            presenter = new MetalScreenPresenter(getContext(),this);
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setMetalView(this);
        }
        initView();
        presenter.loadInfo();
        return view;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(getContext()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new RecyclerViewAdapterMetal());
    }

    @Override
    public void showMetal(List<ActualAllIngot> allIngots, Map<Integer, String> map) {
        mRecyclerView.setAdapter(new RecyclerViewAdapterMetal(allIngots,map));
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
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }
}
