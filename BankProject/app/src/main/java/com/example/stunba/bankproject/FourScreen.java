package com.example.stunba.bankproject;

import com.example.stunba.bankproject.source.entities.ActualAllIngot;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public interface FourScreen {
    interface MetalView{
        void showMetal(List<ActualAllIngot> allIngots, Map<Integer,String> map);
    }
}
