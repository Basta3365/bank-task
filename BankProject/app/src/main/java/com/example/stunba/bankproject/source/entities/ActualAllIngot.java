package com.example.stunba.bankproject.source.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActualAllIngot {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("MetalID")
    @Expose
    private int metalID;
    @SerializedName("Nominal")
    @Expose
    private double nominal;
    @SerializedName("NoCertificateDollars")
    @Expose
    private Object noCertificateDollars;
    @SerializedName("NoCertificateRubles")
    @Expose
    private Object noCertificateRubles;
    @SerializedName("CertificateDollars")
    @Expose
    private Object certificateDollars;
    @SerializedName("CertificateRubles")
    @Expose
    private Object certificateRubles;
    @SerializedName("BanksDollars")
    @Expose
    private Object banksDollars;
    @SerializedName("BanksRubles")
    @Expose
    private Object banksRubles;
    @SerializedName("EntitiesDollars")
    @Expose
    private Object entitiesDollars;
    @SerializedName("EntitiesRubles")
    @Expose
    private Object entitiesRubles;
    private int id;

    public ActualAllIngot(){

    }

    public ActualAllIngot(String date, int metalID, double nominal, Object noCertificateDollars, Object noCertificateRubles, Object certificateDollars, Object certificateRubles, Object banksDollars, Object banksRubles, Object entitiesDollars, Object entitiesRubles) {
        this.date = date;
        this.metalID = metalID;
        this.nominal = nominal;
        this.noCertificateDollars = noCertificateDollars;
        this.noCertificateRubles = noCertificateRubles;
        this.certificateDollars = certificateDollars;
        this.certificateRubles = certificateRubles;
        this.banksDollars = banksDollars;
        this.banksRubles = banksRubles;
        this.entitiesDollars = entitiesDollars;
        this.entitiesRubles = entitiesRubles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMetalID() {
        return metalID;
    }

    public void setMetalID(int metalID) {
        this.metalID = metalID;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    public Object getNoCertificateDollars() {
        return noCertificateDollars;
    }

    public void setNoCertificateDollars(Object noCertificateDollars) {
        this.noCertificateDollars = noCertificateDollars;
    }

    public Object getNoCertificateRubles() {
        return noCertificateRubles;
    }

    public void setNoCertificateRubles(Object noCertificateRubles) {
        this.noCertificateRubles = noCertificateRubles;
    }

    public Object getCertificateDollars() {
        return certificateDollars;
    }

    public void setCertificateDollars(Object certificateDollars) {
        this.certificateDollars = certificateDollars;
    }

    public Object getCertificateRubles() {
        return certificateRubles;
    }

    public void setCertificateRubles(Object certificateRubles) {
        this.certificateRubles = certificateRubles;
    }

    public Object getBanksDollars() {
        return banksDollars;
    }

    public void setBanksDollars(Object banksDollars) {
        this.banksDollars = banksDollars;
    }

    public Object getBanksRubles() {
        return banksRubles;
    }

    public void setBanksRubles(Object banksRubles) {
        this.banksRubles = banksRubles;
    }

    public Object getEntitiesDollars() {
        return entitiesDollars;
    }

    public void setEntitiesDollars(Object entitiesDollars) {
        this.entitiesDollars = entitiesDollars;
    }

    public Object getEntitiesRubles() {
        return entitiesRubles;
    }

    public void setEntitiesRubles(Object entitiesRubles) {
        this.entitiesRubles = entitiesRubles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
