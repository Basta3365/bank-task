package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.MetalName;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandlerMetalName {
    void addMetal(MetalName metalName);

    void getMetal(int id, OnTaskCompleted.LoadComplete onTaskCompleted);

    void getAllMetal(OnTaskCompleted.LoadComplete onTaskCompleted);

    int updateMetal(MetalName rate);

    void deleteMetal(MetalName rate);

    void deleteAll();
}
