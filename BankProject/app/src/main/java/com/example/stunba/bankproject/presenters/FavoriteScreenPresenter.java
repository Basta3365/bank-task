package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.example.stunba.bankproject.interfaces.FavoriteView;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.R;
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

public class FavoriteScreenPresenter implements IFavoriteScreen {
    private Repository repository;
    private FavoriteView favoriteView;
    private List<ActualRate> actualFavorites;
    private Set<String> abbFavorite;
    private List<Currency> currencyList;


    public FavoriteScreenPresenter(Context context, FavoriteView view) {
        repository = Repository.getInstance(context);
        favoriteView = view;
        currencyList = new ArrayList<>();
        actualFavorites = new ArrayList<>();
        abbFavorite = new HashSet<>();
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
                        if (favoriteView != null)
                            favoriteView.setDataForAdapter(actualFavorites);

                    }
                }
            });
        } else {
            if (favoriteView != null)
                favoriteView.setDataForAdapter(actualFavorites);
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
                                            if (favoriteView != null)
                                                favoriteView.setDataForAdapter(actualFavorites);
                                        }
                                    } else {
                                        if (favoriteView != null) {
                                            favoriteView.showError("No information");
                                        }
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
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

    public void deleteItem(ActualRate actualRate) {
        actualFavorites.remove(actualRate);
        abbFavorite.remove(actualRate.getCurAbbreviation());
        repository.deleteFavorite(actualRate);
        if (favoriteView != null)
            favoriteView.setDataForAdapter(actualFavorites);
    }

    public void setView(FavoriteView favoriteView) {
        this.favoriteView = favoriteView;
    }
}
