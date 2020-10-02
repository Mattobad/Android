package com.example.clientApi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**Class to make the data in the API
 * with Object Access Getters and Setters
 * */
public class Stories {



    @SerializedName("stories")
    @Expose
    // list object of stories
    private List<Users> userData = null;

    public Stories(List<Users> userData) {
        this.userData = userData;
    }

    public List<Users> getUserData() {
        return userData;
    }

    public void setUserData(List<Users> userData) {
        this.userData = userData;
    }


}
