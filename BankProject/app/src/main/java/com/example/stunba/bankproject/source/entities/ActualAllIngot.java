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
    private String noCertificateDollars;
    @SerializedName("NoCertificateRubles")
    @Expose
    private String noCertificateRubles;
    @SerializedName("CertificateDollars")
    @Expose
    private String certificateDollars;
    @SerializedName("CertificateRubles")
    @Expose
    private String certificateRubles;
    @SerializedName("BanksDollars")
    @Expose
    private String banksDollars;
    @SerializedName("BanksRubles")
    @Expose
    private String banksRubles;
    @SerializedName("EntitiesDollars")
    @Expose
    private String entitiesDollars;
    @SerializedName("EntitiesRubles")
    @Expose
    private String entitiesRubles;
    private int id;

    public ActualAllIngot() {

    }

    public ActualAllIngot(String date, int metalID, double nominal, String noCertificateDollars, String noCertificateRubles, String certificateDollars, String certificateRubles, String banksDollars, String banksRubles, String entitiesDollars, String entitiesRubles) {
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

    public String getNoCertificateDollars() {
        return noCertificateDollars;
    }

    public void setNoCertificateDollars(String noCertificateDollars) {
        this.noCertificateDollars = noCertificateDollars;
    }

    public String getNoCertificateRubles() {
        return noCertificateRubles;
    }

    public void setNoCertificateRubles(String noCertificateRubles) {
        this.noCertificateRubles = noCertificateRubles;
    }

    public String getCertificateDollars() {
        return certificateDollars;
    }

    public void setCertificateDollars(String certificateDollars) {
        this.certificateDollars = certificateDollars;
    }

    public String getCertificateRubles() {
        return certificateRubles;
    }

    public void setCertificateRubles(String certificateRubles) {
        this.certificateRubles = certificateRubles;
    }

    public String getBanksDollars() {
        return banksDollars;
    }

    public void setBanksDollars(String banksDollars) {
        this.banksDollars = banksDollars;
    }

    public String getBanksRubles() {
        return banksRubles;
    }

    public void setBanksRubles(String banksRubles) {
        this.banksRubles = banksRubles;
    }

    public String getEntitiesDollars() {
        return entitiesDollars;
    }

    public void setEntitiesDollars(String entitiesDollars) {
        this.entitiesDollars = entitiesDollars;
    }

    public String getEntitiesRubles() {
        return entitiesRubles;
    }

    public void setEntitiesRubles(String entitiesRubles) {
        this.entitiesRubles = entitiesRubles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
