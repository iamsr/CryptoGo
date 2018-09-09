package com.example.shubhamr.cryptogo.ModelClasses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class CoinIndexResponse {

    @SerializedName("Response")
    private String response;

    @SerializedName("Message")
    private String message;

    @SerializedName("Data")
    private JsonElement coinList;

    @SerializedName("BaseImageUrl")
    private String baseImageUrl;

    @SerializedName("BaseLinkUrl")
    private String baseLinkUrl;


    public JsonElement getCoinList() {
        return coinList;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public String getBaseLinkUrl() {
        return baseLinkUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public void setCoinList(JsonElement coinList) {
        this.coinList = coinList;
    }

    public void setBaseLinkUrl(String baseLinkUrl) {
        this.baseLinkUrl = baseLinkUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponse(String response) {
        this.response = response;
    }







}
