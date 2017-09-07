package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.MetalName;

import java.util.List;

/**
 * Created by Kseniya_Bastun on 8/29/2017.
 */

public interface IDatabaseHandlerMetalName {
    void addMetal(MetalName metalName);
    MetalName getMetal(int id);
    void getAllMetal(OnTaskCompleted.LoadComplete onTaskCompleted);
    int getMetalCount();
    int updateMetal(MetalName rate);
    void deleteMetal(MetalName rate);
    void deleteAll();
}
