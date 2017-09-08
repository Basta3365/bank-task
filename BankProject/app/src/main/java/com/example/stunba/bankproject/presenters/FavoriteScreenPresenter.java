package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.FavoriteSceen;
import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterFavorites;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.Currency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kseniya_Bastun on 9/8/2017.
 */

public class FavoriteScreenPresenter extends BasePresenter<List<String>,FavoriteSceen.FavoriteView>{
    private Repository repository;
    private FavoriteSceen.FavoriteView favoriteView;
    private RecyclerViewAdapterFavorites recyclerViewAdapterFavorites;
    private List<ActualRate> actualFavorites =null;
    private Set<String> abbfavorite=new HashSet();
    private List<Currency> currencyList;

    public RecyclerViewAdapterFavorites getRecyclerViewAdapterFavorites() {
        return recyclerViewAdapterFavorites;
    }

    public void setRecyclerViewAdapterFavorites(RecyclerViewAdapterFavorites recyclerViewAdapterFavorites) {
        this.recyclerViewAdapterFavorites = recyclerViewAdapterFavorites;
    }

    public FavoriteScreenPresenter(Context context, FavoriteSceen.FavoriteView view){
        repository=Repository.getInstance(context);
        favoriteView=view;
        recyclerViewAdapterFavorites=new RecyclerViewAdapterFavorites();
    }
    public void loadInfo() {
        repository.getAllFavorites(new OnTaskCompleted.FavoritePresenter() {
            @Override
            public void onAllFavorites(Object o) {
                if(o!=null){
                    actualFavorites =(List<ActualRate>)o;
                    for (ActualRate rate: actualFavorites) {
                        abbfavorite.add(rate.getCurAbbreviation());
                    }
                    setRecyclerViewAdapterFavorites(new RecyclerViewAdapterFavorites((List<ActualRate>) o, new OnTaskCompleted.LoadComplete() {
                        @Override
                        public void onLoadComplete(Object o) {
                            actualFavorites.remove((ActualRate)o);
                            abbfavorite.remove(((ActualRate)o).getCurAbbreviation());
                            getRecyclerViewAdapterFavorites().setFavorites(actualFavorites);
                            getRecyclerViewAdapterFavorites().notifyDataSetChanged();
                            repository.deleteFavorite((ActualRate)o);
                        }
                    }));
                    favoriteView.showFavorite();

                }
            }
        });
        repository.getAllCurrencies(new OnTaskCompleted.DynamicPresenterCompleteCurrency() {
            @Override
            public void onAllCurrencyLoad(Object o) {
                currencyList=(List<Currency>)o;
            }
        });
    }

    public void addFavorite() {
        repository.getRateByAbb("RUB", new OnTaskCompleted.MainPresenterComplete() {
            @Override
            public void onLoadRate(Object o) {
                //TODO kostyl
                ActualRate actualRate= (ActualRate) o;
                if(!abbfavorite.contains(actualRate.getCurAbbreviation())) {
                    actualFavorites.add(actualRate);
                    abbfavorite.add(actualRate.getCurAbbreviation());
                    repository.addFavorite(actualRate);
                    getRecyclerViewAdapterFavorites().setFavorites(actualFavorites);
                    getRecyclerViewAdapterFavorites().notifyDataSetChanged();
                    favoriteView.showFavorite();
                }
            }
        });
    }
}
