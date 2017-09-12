package com.example.stunba.bankproject.presenters;


/**
 * Created by Kseniya_Bastun on 8/23/2017.
 */

public abstract class BasePresenter<V> {
    private V view;

    public void bindView(V view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }

}