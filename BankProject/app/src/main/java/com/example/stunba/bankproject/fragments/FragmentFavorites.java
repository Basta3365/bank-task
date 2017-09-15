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

import com.example.stunba.bankproject.interfaces.FavoriteScreen;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.SimpleDividerDecoration;
import com.example.stunba.bankproject.presenters.FavoriteScreenPresenter;
import com.example.stunba.bankproject.presenters.ipresenters.IFavoriteScreen;
import com.example.stunba.bankproject.presenters.PresenterManager;

/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public class FragmentFavorites extends Fragment implements FavoriteScreen.FavoriteView {
    private View view;
    private RecyclerView mRecyclerView;
    private IFavoriteScreen presenter;
    private ImageButton buttonAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_favorites, container, false);
        if (savedInstanceState == null) {
            presenter = new FavoriteScreenPresenter(getContext(), this);
        } else {
            presenter = (FavoriteScreenPresenter) PresenterManager.getInstance().restorePresenter(savedInstanceState);
            presenter.setView(this);
        }
        initView();
        presenter.loadInfo();
        return view;
    }

    private void initView() {
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
        mRecyclerView.setAdapter(presenter.getRecyclerViewAdapterFavorites());
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
    public void showError() {
        Toast.makeText(getContext(), "No information", Toast.LENGTH_SHORT).show();
    }
}
