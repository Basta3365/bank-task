package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandlerMetalRate {
    void addIngot(ActualAllIngot allIngot);

    void getIngot(int id, OnTaskCompleted.MetalLoad onTaskCompleted);

    void getAllIngots(OnTaskCompleted.MetalLoadAll onTaskCompleted);

    int updateIngot(ActualAllIngot rate);

    void deleteIngot(ActualAllIngot rate);

    void deleteAll();
}
