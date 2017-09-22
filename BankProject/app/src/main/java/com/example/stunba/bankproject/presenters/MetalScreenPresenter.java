package com.example.stunba.bankproject.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.stunba.bankproject.interfaces.MetalView;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterMetal;
import com.example.stunba.bankproject.presenters.ipresenters.IMetalScreen;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class MetalScreenPresenter implements IMetalScreen {
    private Repository repository;
    private MetalView metalView;
    private RecyclerViewAdapterMetal recyclerViewAdapterMetal;
    private List<ActualAllIngot> actualAllIngots;
    private Map<Integer, MetalName> values;

    public MetalScreenPresenter(Context context, MetalView view) {
        repository = Repository.getInstance(context);
        metalView = view;
        recyclerViewAdapterMetal = new RecyclerViewAdapterMetal(context);
        actualAllIngots = new ArrayList<>();
        values = new HashMap<>();
    }

    public void loadInfo() {
        repository.getAllMetalNames(new OnTaskCompleted.MetalNamesLoadAll() {
            @Override
            public void onAllNames(final List<MetalName> o) {
                if (o != null) {
                    repository.getAllIngots(new OnTaskCompleted.MetalLoadAll() {
                        @Override
                        public void onAllIngot(List<ActualAllIngot> load) {
                            if (load != null) {
                                actualAllIngots.addAll(load);
                                for (MetalName metal : o) {
                                    values.put(metal.getId(), metal);
                                }
                                recyclerViewAdapterMetal.setActualData(load);
                                recyclerViewAdapterMetal.setNames(values);
                                recyclerViewAdapterMetal.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public RecyclerView.Adapter getRecyclerViewAdapterMetal() {
        return recyclerViewAdapterMetal;
    }

    @Override
    public MetalView getView() {
        return metalView;
    }

    @Override
    public void setView(MetalView view) {
        metalView = view;
    }
}
