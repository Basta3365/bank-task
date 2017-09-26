package com.example.stunba.bankproject.presenters.ipresenters;

import android.support.v4.app.FragmentActivity;

import com.example.stunba.bankproject.interfaces.FavoriteView;
import com.example.stunba.bankproject.source.entities.ActualRate;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IFavoriteScreen extends BaseInterface {
    void loadInfo();

    void addFavorite(final FragmentActivity activity);

    void deleteItem(ActualRate actualRate);

    void setView(FavoriteView favoriteView);
}
