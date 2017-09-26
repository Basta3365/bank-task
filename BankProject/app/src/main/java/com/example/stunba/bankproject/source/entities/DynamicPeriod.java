package com.example.stunba.bankproject.source.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kseniya_Bastun on 8/25/2017.
 */


public class DynamicPeriod {

    @SerializedName("Cur_ID")
    @Expose
    private int curID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Cur_OfficialRate")
    @Expose
    private double curOfficialRate;

    public int getCurID() {
        return curID;
    }

    public void setCurID(int curID) {
        this.curID = curID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCurOfficialRate() {
        return curOfficialRate;
    }

    public void setCurOfficialRate(double curOfficialRate) {
        this.curOfficialRate = curOfficialRate;
    }

}