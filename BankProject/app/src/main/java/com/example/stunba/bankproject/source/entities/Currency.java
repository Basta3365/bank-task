package com.example.stunba.bankproject.source.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @SerializedName("Cur_ID")
    @Expose
    private int curID;
    @SerializedName("Cur_ParentID")
    @Expose
    private int curParentID;
    @SerializedName("Cur_Code")
    @Expose
    private String curCode;
    @SerializedName("Cur_Abbreviation")
    @Expose
    private String curAbbreviation;
    @SerializedName("Cur_Name")
    @Expose
    private String curName;
    @SerializedName("Cur_Name_Bel")
    @Expose
    private String curNameBel;
    @SerializedName("Cur_Name_Eng")
    @Expose
    private String curNameEng;
    @SerializedName("Cur_QuotName")
    @Expose
    private String curQuotName;
    @SerializedName("Cur_QuotName_Bel")
    @Expose
    private String curQuotNameBel;
    @SerializedName("Cur_QuotName_Eng")
    @Expose
    private String curQuotNameEng;
    @SerializedName("Cur_NameMulti")
    @Expose
    private String curNameMulti;
    @SerializedName("Cur_Name_BelMulti")
    @Expose
    private String curNameBelMulti;
    @SerializedName("Cur_Name_EngMulti")
    @Expose
    private String curNameEngMulti;
    @SerializedName("Cur_Scale")
    @Expose
    private int curScale;
    @SerializedName("Cur_Periodicity")
    @Expose
    private int curPeriodicity;
    @SerializedName("Cur_DateStart")
    @Expose
    private String curDateStart;
    @SerializedName("Cur_DateEnd")
    @Expose
    private String curDateEnd;

    public Currency(){
    }

    public Currency(int curID, int curParentID, String curCode, String curAbbreviation, String curName, String curNameBel, String curNameEng, String curQuotName, String curQuotNameBel, String curQuotNameEng, String curNameMulti, String curNameBelMulti, String curNameEngMulti, int curScale, int curPeriodicity, String curDateStart, String curDateEnd) {
        this.curID = curID;
        this.curParentID = curParentID;
        this.curCode = curCode;
        this.curAbbreviation = curAbbreviation;
        this.curName = curName;
        this.curNameBel = curNameBel;
        this.curNameEng = curNameEng;
        this.curQuotName = curQuotName;
        this.curQuotNameBel = curQuotNameBel;
        this.curQuotNameEng = curQuotNameEng;
        this.curNameMulti = curNameMulti;
        this.curNameBelMulti = curNameBelMulti;
        this.curNameEngMulti = curNameEngMulti;
        this.curScale = curScale;
        this.curPeriodicity = curPeriodicity;
        this.curDateStart = curDateStart;
        this.curDateEnd = curDateEnd;
    }

    public int getCurID() {
        return curID;
    }

    public void setCurID(int curID) {
        this.curID = curID;
    }

    public int getCurParentID() {
        return curParentID;
    }

    public void setCurParentID(int curParentID) {
        this.curParentID = curParentID;
    }

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurNameBel() {
        return curNameBel;
    }

    public void setCurNameBel(String curNameBel) {
        this.curNameBel = curNameBel;
    }

    public String getCurNameEng() {
        return curNameEng;
    }

    public void setCurNameEng(String curNameEng) {
        this.curNameEng = curNameEng;
    }

    public String getCurQuotName() {
        return curQuotName;
    }

    public void setCurQuotName(String curQuotName) {
        this.curQuotName = curQuotName;
    }

    public String getCurQuotNameBel() {
        return curQuotNameBel;
    }

    public void setCurQuotNameBel(String curQuotNameBel) {
        this.curQuotNameBel = curQuotNameBel;
    }

    public String getCurQuotNameEng() {
        return curQuotNameEng;
    }

    public void setCurQuotNameEng(String curQuotNameEng) {
        this.curQuotNameEng = curQuotNameEng;
    }

    public String getCurNameMulti() {
        return curNameMulti;
    }

    public void setCurNameMulti(String curNameMulti) {
        this.curNameMulti = curNameMulti;
    }

    public String getCurNameBelMulti() {
        return curNameBelMulti;
    }

    public void setCurNameBelMulti(String curNameBelMulti) {
        this.curNameBelMulti = curNameBelMulti;
    }

    public String getCurNameEngMulti() {
        return curNameEngMulti;
    }

    public void setCurNameEngMulti(String curNameEngMulti) {
        this.curNameEngMulti = curNameEngMulti;
    }

    public int getCurScale() {
        return curScale;
    }

    public void setCurScale(int curScale) {
        this.curScale = curScale;
    }

    public int getCurPeriodicity() {
        return curPeriodicity;
    }

    public void setCurPeriodicity(int curPeriodicity) {
        this.curPeriodicity = curPeriodicity;
    }

    public String getCurDateStart() {
        return curDateStart;
    }

    public void setCurDateStart(String curDateStart) {
        this.curDateStart = curDateStart;
    }

    public String getCurDateEnd() {
        return curDateEnd;
    }

    public void setCurDateEnd(String curDateEnd) {
        this.curDateEnd = curDateEnd;
    }

}
