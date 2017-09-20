package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.example.stunba.bankproject.interfaces.FavoriteScreen;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterFavorites;
import com.example.stunba.bankproject.presenters.ipresenters.IFavoriteScreen;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kseniya_Bastun on 9/8/2017.
 */

public class FavoriteScreenPresenter implements IFavoriteScreen, OnTaskCompleted.DeleteFavorite {
    private Repository repository;
    private FavoriteScreen.FavoriteView favoriteView;
    private RecyclerViewAdapterFavorites recyclerViewAdapterFavorites;
    private List<ActualRate> actualFavorites;
    private Set<String> abbFavorite;
    private List<Currency> currencyList;

    public RecyclerViewAdapterFavorites getRecyclerViewAdapterFavorites() {
        return recyclerViewAdapterFavorites;
    }

    public FavoriteScreenPresenter(Context context, FavoriteScreen.FavoriteView view) {
        repository = Repository.getInstance(context);
        favoriteView = view;
        recyclerViewAdapterFavorites = new RecyclerViewAdapterFavorites(this);
        currencyList = new ArrayList<>();
        actualFavorites = new ArrayList<>();
        abbFavorite = new HashSet();
    }

    public void loadInfo() {
        if (actualFavorites.size() == 0) {
            repository.getAllFavorites(new OnTaskCompleted.LoadAllActualRate() {
                @Override
                public void onLoadAllRate(List<ActualRate> o) {
                    if (o != null) {
                        actualFavorites = o;
                        for (ActualRate rate : actualFavorites) {
                            abbFavorite.add(rate.getCurAbbreviation());
                        }
                        getRecyclerViewAdapterFavorites().setFavorites((List<ActualRate>) o);
                        getRecyclerViewAdapterFavorites().notifyDataSetChanged();

                    }
                }
            });
        }
        if (currencyList.size() == 0) {
            repository.getAllCurrencies(new OnTaskCompleted.LoadAllCurrencies() {
                @Override
                public void onAllCurrencyLoad(List<Currency> o) {
                    currencyList = o;
                }
            });
        }
    }

    public void addFavorite(final FragmentActivity activity) {
        final CharSequence[] cs = new CharSequence[currencyList.size()];
        for (int i = 0; i < currencyList.size(); i++) {
            cs[i] = currencyList.get(i).getCurAbbreviation();
        }
        final String[] abb = {new String()};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.select_currency)
                .setCancelable(true)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (abb[0] != null) {
                            repository.getRateByAbb(abb[0], new OnTaskCompleted.LoadActualRate() {
                                @Override
                                public void onLoadRate(ActualRate actualRate) {
                                    if (actualRate != null) {
                                        if (!abbFavorite.contains(actualRate.getCurAbbreviation())) {
                                            actualFavorites.add(actualRate);
                                            abbFavorite.add(actualRate.getCurAbbreviation());
                                            repository.addFavorite(actualRate);
                                            getRecyclerViewAdapterFavorites().setFavorites(actualFavorites);
                                            getRecyclerViewAdapterFavorites().notifyDataSetChanged();
                                        }
                                    } else {
                                        favoriteView.showError();
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();

                            }
                        })
                .setSingleChoiceItems(cs, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                abb[0] = cs[item].toString();
                            }
                        });
        builder.create().show();
    }

    @Override
    public void onDeleteItem(ActualRate actualRate) {
        actualFavorites.remove(actualRate);
        abbFavorite.remove(actualRate.getCurAbbreviation());
        getRecyclerViewAdapterFavorites().setFavorites(actualFavorites);
        getRecyclerViewAdapterFavorites().notifyDataSetChanged();
        repository.deleteFavorite(actualRate);
    }


    @Override
    public FavoriteScreen.FavoriteView getView() {
        return favoriteView;
    }

    @Override
    public void setView(FavoriteScreen.FavoriteView view) {
        favoriteView = view;
    }
}
