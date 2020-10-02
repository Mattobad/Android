package com.example.clientApi.utils;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstace {

    private static Retrofit retrofit;
    private static final String BASE_URL = "....";

    public static Retrofit getRetrofitInstance() {


        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(client)
                    .build();
        }
        return retrofit;
    }
}
