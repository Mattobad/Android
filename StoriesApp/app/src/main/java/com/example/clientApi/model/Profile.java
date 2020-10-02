package com.example.clientApi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO class for auther name
 * */
public class Profile {


    @SerializedName("fullname")
    @Expose
    private String autherName;

    public String getAutherName() {
        return autherName;
    }

    public void setAutherName(String autherName) {
        this.autherName = autherName;
    }
}
