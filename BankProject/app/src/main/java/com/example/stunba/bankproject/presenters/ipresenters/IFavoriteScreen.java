package com.example.stunba.bankproject.presenters.ipresenters;

import android.support.v4.app.FragmentActivity;

import com.example.stunba.bankproject.adapter.RecyclerViewAdapterFavorites;
import com.example.stunba.bankproject.interfaces.FavoriteView;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IFavoriteScreen extends BaseInterface {
    FavoriteView getView();

    void setView(FavoriteView view);

    void loadInfo();

    void addFavorite(final FragmentActivity activity);

    RecyclerViewAdapterFavorites getRecyclerViewAdapterFavorites();
}
