package com.example.stunba.bankproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stunba.bankproject.interfaces.MetalScreen;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.SimpleDividerDecoration;
import com.example.stunba.bankproject.presenters.ipresenters.IMetalScreen;
import com.example.stunba.bankproject.presenters.MetalScreenPresenter;
import com.example.stunba.bankproject.presenters.PresenterManager;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class RecyclerFragmentMetal extends Fragment implements MetalScreen.MetalView {
    private RecyclerView mRecyclerView;
    private View view;
    private IMetalScreen presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.recycler_view, container, false);
        }
        if (savedInstanceState == null) {
            presenter = new MetalScreenPresenter(getContext(), this);
        } else {
            presenter = (IMetalScreen) PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setView(this);
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
        mRecyclerView.setAdapter(presenter.getRecyclerViewAdapterMetal());
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
}
