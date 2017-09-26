package com.example.stunba.bankproject.source.local;

import com.example.stunba.bankproject.interfaces.OnTaskCompleted;
import com.example.stunba.bankproject.source.entities.ActualRate;

/**
 * Created by Kseniya_Bastun on 9/5/2017.
 */

public interface IDatabaseHandlerFavorites {
    void addFavorite(ActualRate rate);

    void getAllFavorites(OnTaskCompleted.LoadAllActualRate loadAllActualRate);

    int updateFavorite(ActualRate rate);

    void deleteFavorite(ActualRate rate);

    void deleteAll();
}
