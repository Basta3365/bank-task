package com.example.stunba.bankproject.presenters.ipresenters;

import com.example.stunba.bankproject.interfaces.MetalView;

/**
 * Created by Kseniya_Bastun on 9/15/2017.
 */

public interface IMetalScreen extends BaseInterface {

    void loadInfo();

    void setView(MetalView metalView);

}
