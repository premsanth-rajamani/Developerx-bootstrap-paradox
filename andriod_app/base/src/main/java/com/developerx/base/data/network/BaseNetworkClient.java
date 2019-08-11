package com.developerx.base.data.network;

import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BaseNetworkClient {
    private final Retrofit retrofit;
    private final Gson gson;

    @Inject
    public BaseNetworkClient(Retrofit retrofit, Gson gson) {
        this.retrofit = retrofit;
        this.gson = gson;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }
}