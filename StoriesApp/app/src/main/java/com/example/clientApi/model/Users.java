package com.example.clientApi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO(Plain old java Object) class for authors
 * */
public class Users {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("user")
    @Expose
    // author profile for users
    private Profile author;

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    @SerializedName("cover")
    @Expose
    private String coverImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }


}
