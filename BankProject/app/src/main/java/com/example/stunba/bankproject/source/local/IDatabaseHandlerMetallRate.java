package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualAllIngot;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandlerMetallRate {
    void addIngot(ActualAllIngot allIngot);
    ActualAllIngot getIngot(int id);
    void getAllIngots(OnTaskCompleted.LoadComplete onTaskCompleted);
    int getIngotsCount();
    int updateIngot(ActualAllIngot rate);
    void deleteIngot(ActualAllIngot rate);
    void deleteAll();
}
