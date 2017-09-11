package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.example.stunba.bankproject.FavoriteSceen;
import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.R;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterFavorites;
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

public class FavoriteScreenPresenter extends BasePresenter<List<String>, FavoriteSceen.FavoriteView> {
    private Repository repository;
    private FavoriteSceen.FavoriteView favoriteView;
    private RecyclerViewAdapterFavorites recyclerViewAdapterFavorites;
    private List<ActualRate> actualFavorites = null;
    private Set<String> abbFavorite = new HashSet();
    private List<Currency> currencyList;

    public void setFavoriteView(FavoriteSceen.FavoriteView favoriteView) {
        this.favoriteView = favoriteView;
    }

    public RecyclerViewAdapterFavorites getRecyclerViewAdapterFavorites() {
        return recyclerViewAdapterFavorites;
    }

    public void setRecyclerViewAdapterFavorites(RecyclerViewAdapterFavorites recyclerViewAdapterFavorites) {
        this.recyclerViewAdapterFavorites = recyclerViewAdapterFavorites;
    }

    public FavoriteScreenPresenter(Context context, FavoriteSceen.FavoriteView view) {
        repository = Repository.getInstance(context);
        favoriteView = view;
        recyclerViewAdapterFavorites = new RecyclerViewAdapterFavorites();
        currencyList = new ArrayList<>();
    }

    public void loadInfo() {
        repository.getAllFavorites(new OnTaskCompleted.FavoritePresenter() {
            @Override
            public void onAllFavorites(Object o) {
                if (o != null) {
                    actualFavorites = (List<ActualRate>) o;
                    for (ActualRate rate : actualFavorites) {
                        abbFavorite.add(rate.getCurAbbreviation());
                    }
                    setRecyclerViewAdapterFavorites(new RecyclerViewAdapterFavorites((List<ActualRate>) o, new OnTaskCompleted.LoadComplete() {
                        @Override
                        public void onLoadComplete(Object o) {
                            actualFavorites.remove((ActualRate) o);
                            abbFavorite.remove(((ActualRate) o).getCurAbbreviation());
                            getRecyclerViewAdapterFavorites().setFavorites(actualFavorites);
                            getRecyclerViewAdapterFavorites().notifyDataSetChanged();
                            repository.deleteFavorite((ActualRate) o);
                        }
                    }));
                    favoriteView.showFavorite();

                }
            }
        });
        repository.getAllCurrencies(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
            @Override
            public void onAllCurrencyLoad(Object o) {
                currencyList = (List<Currency>) o;
            }
        });
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
                            repository.getRateByAbb(abb[0], new OnTaskCompleted.MainPresenterComplete() {
                                @Override
                                public void onLoadRate(Object o) {
                                    ActualRate actualRate = (ActualRate) o;
                                    if (!abbFavorite.contains(actualRate.getCurAbbreviation())) {
                                        actualFavorites.add(actualRate);
                                        abbFavorite.add(actualRate.getCurAbbreviation());
                                        repository.addFavorite(actualRate);
                                        getRecyclerViewAdapterFavorites().setFavorites(actualFavorites);
                                        getRecyclerViewAdapterFavorites().notifyDataSetChanged();
                                        favoriteView.showFavorite();
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
}
