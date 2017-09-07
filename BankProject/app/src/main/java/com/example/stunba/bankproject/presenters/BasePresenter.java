package com.example.stunba.bankproject.presenters;


/**
 * Created by Kseniya_Bastun on 8/23/2017.
 */

public abstract class BasePresenter<M, V> {

    protected M model;
    private V view;

    public void setModel(M model) {
        resetState();
        this.model = model;
    }

    protected void resetState() {
    }

    public void bindView(V view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }
    protected V view() {
            return view;
    }

    protected boolean setupDone() {
        return view() != null && model != null;
    }

}