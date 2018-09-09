package com.example.shubhamr.cryptogo.Interface;

import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinIndexResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


    @GET("all/coinlist")
    Call<CoinIndexResponse> getCoinList();




}
