package com.example.stunba.bankproject.source.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetalName {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("NameBel")
    @Expose
    private String nameBel;
    @SerializedName("NameEng")
    @Expose
    private String nameEng;

    public MetalName(){

    }
    public MetalName(int id, String name, String nameBel, String nameEng) {
        this.id = id;
        this.name = name;
        this.nameBel = nameBel;
        this.nameEng = nameEng;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameBel() {
        return nameBel;
    }

    public void setNameBel(String nameBel) {
        this.nameBel = nameBel;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

}
