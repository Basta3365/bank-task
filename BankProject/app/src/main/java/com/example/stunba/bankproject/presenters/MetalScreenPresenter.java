package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.interfaces.FourScreen;
import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterMetal;
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

public class MetalScreenPresenter extends BasePresenter<FourScreen.MetalView> {
    private Repository repository;
    private FourScreen.MetalView metalView;
    private RecyclerViewAdapterMetal recyclerViewAdapterMetal;
    private List<ActualAllIngot> actualAllIngots;
    private Map<Integer, MetalName> values;

    public RecyclerViewAdapterMetal getRecyclerViewAdapterMetal() {
        return recyclerViewAdapterMetal;
    }

    public void setRecyclerViewAdapterMetal(RecyclerViewAdapterMetal recyclerViewAdapterMetal) {

        this.recyclerViewAdapterMetal = recyclerViewAdapterMetal;
    }

    public void setMetalView(FourScreen.MetalView metalView) {
        this.metalView = metalView;
    }

    public MetalScreenPresenter(Context context, FourScreen.MetalView view) {
        repository = Repository.getInstance(context);
        metalView = view;
        recyclerViewAdapterMetal = new RecyclerViewAdapterMetal(context);
        actualAllIngots = new ArrayList<>();
        values = new HashMap<>();
    }

    public void loadInfo() {
        repository.getAllMetalNames(new OnTaskCompleted.LoadComplete() {
            @Override
            public void onLoadComplete(final Object o) {
                if (o != null) {
                    repository.getAllIngots(new OnTaskCompleted.LoadComplete() {
                        @Override
                        public void onLoadComplete(Object load) {
                            if (load != null) {
                                actualAllIngots.addAll((List<ActualAllIngot>) load);
                                for (MetalName metal : (List<MetalName>) o) {
                                    values.put(metal.getId(), metal);
                                }
                                recyclerViewAdapterMetal.setActualData((List<ActualAllIngot>) load);
                                recyclerViewAdapterMetal.setNames(values);
                                recyclerViewAdapterMetal.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

}
