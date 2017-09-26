package com.example.stunba.bankproject.interfaces;

import com.example.stunba.bankproject.source.entities.ActualAllIngot;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.List;
import java.util.Map;

/**
 * Created by Kseniya_Bastun on 9/6/2017.
 */

public interface MetalView {
    void setDataForAdapter(List<ActualAllIngot> load, Map<Integer, MetalName> data);
}
