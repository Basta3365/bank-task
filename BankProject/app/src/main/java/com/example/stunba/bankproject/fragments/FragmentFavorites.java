package com.example.stunba.bankproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.stunba.bankproject.adapter.RecyclerViewAdapterFavorites;
import com.example.stunba.bankproject.interfaces.FavoriteView;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.SimpleDividerDecoration;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.presenters.FavoriteScreenPresenter;
import com.example.stunba.bankproject.presenters.ipresenters.IFavoriteScreen;
import com.example.stunba.bankproject.presenters.PresenterManager;
import com.example.stunba.bankproject.source.entities.ActualRate;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public class FragmentFavorites extends Fragment implements FavoriteView, OnTaskCompleted.DeleteFavorite {
    private RecyclerView mRecyclerView;
    private IFavoriteScreen presenter;
    private ImageButton buttonAdd;
    private RecyclerViewAdapterFavorites recyclerViewAdapterFavorites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        if (savedInstanceState == null) {
            presenter = new FavoriteScreenPresenter(getContext(), this);
        } else {
            presenter = (FavoriteScreenPresenter) PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setView(this);
        }
        initView(view);
        presenter.loadInfo();
        return view;
    }

    private void initView(View view) {
        recyclerViewAdapterFavorites = new RecyclerViewAdapterFavorites(this);
        buttonAdd = (ImageButton) view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addFavorite(getActivity());

            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_favorites);
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(getContext()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(recyclerViewAdapterFavorites);
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
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataForAdapter(List<ActualRate> data) {
        recyclerViewAdapterFavorites.setFavorites(data);
        recyclerViewAdapterFavorites.notifyDataSetChanged();
    }

    @Override
    public void onDeleteItem(ActualRate actualRate) {
        presenter.deleteItem(actualRate);
    }
}
