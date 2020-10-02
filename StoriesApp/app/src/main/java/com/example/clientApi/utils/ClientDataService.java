package com.example.clientApi.utils;

import com.example.clientApi.model.Stories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientDataService {

    @GET(".......")
    public Call<Stories> getStories();
}
