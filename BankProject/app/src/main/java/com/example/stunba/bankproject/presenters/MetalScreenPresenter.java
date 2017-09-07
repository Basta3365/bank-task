package com.example.stunba.bankproject.presenters;

import android.content.Context;

import com.example.stunba.bankproject.FourScreen;
import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.OneScreen;
import com.example.stunba.bankproject.adapter.RecyclerViewAdapterMetal;
import com.example.stunba.bankproject.source.Repository;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.ActualRate;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public class MetalScreenPresenter extends BasePresenter<List<String>,FourScreen.MetalView> {
    private Repository repository;
    private FourScreen.MetalView metalView;
    public MetalScreenPresenter(Context context, FourScreen.MetalView view){
        repository=Repository.getInstance(context);
        metalView=view;
    }
    public void loadInfo() {
        repository.getAllMetalNames(new OnTaskCompleted.LoadComplete() {
            @Override
            public void onLoadComplete(final Object o) {
                if(o!=null){
                   repository.getAllIngots(new OnTaskCompleted.LoadComplete() {
                        @Override
                        public void onLoadComplete(Object load) {
                            if(load!=null){
                                Map<Integer,String> values=new HashMap<Integer, String>();
                                for (MetalName metal:(List<MetalName>)o) {
                                    values.put(metal.getId(),metal.getName());
                                }
                                metalView.showMetal((List<ActualAllIngot>) load,values);
                            }
                        }
                    });
                }
            }
        });
    }

}
