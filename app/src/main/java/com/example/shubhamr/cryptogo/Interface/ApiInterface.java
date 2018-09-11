package com.example.shubhamr.cryptogo.Interface;

import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinIndexResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("all/coinlist")
    Call<CoinIndexResponse> getCoinList();








    @GET("pricemultifull")
    Call<JsonObject> getCoinData(@Query("fsyms") String symbol,@Query("tsyms")String currency);

    @GET("histoday")
    Call<JsonObject> getCoinHistory(@Query("fsym") String symbol,@Query("tsym")String currency,@Query("limit")String limit);




}
