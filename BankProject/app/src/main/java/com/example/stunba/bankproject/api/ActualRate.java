
package com.example.stunba.bankproject.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActualRate {

    @SerializedName("Cur_ID")
    @Expose
    private int curID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Cur_Abbreviation")
    @Expose
    private String curAbbreviation;
    @SerializedName("Cur_Scale")
    @Expose
    private int curScale;
    @SerializedName("Cur_Name")
    @Expose
    private String curName;
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

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public int getCurScale() {
        return curScale;
    }

    public void setCurScale(int curScale) {
        this.curScale = curScale;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public double getCurOfficialRate() {
        return curOfficialRate;
    }

    public void setCurOfficialRate(double curOfficialRate) {
        this.curOfficialRate = curOfficialRate;
    }

}
