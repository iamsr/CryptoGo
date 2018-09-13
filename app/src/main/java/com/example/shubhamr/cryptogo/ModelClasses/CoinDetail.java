package com.example.shubhamr.cryptogo.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoinDetail {


    @SerializedName("PRICE")
    private String price;

    @SerializedName("HIGHDAY")
    private String high;

    @SerializedName("LOWDAY")
    private String low;

    @SerializedName("CHANGEPCT24HOUR")
    private String changePercent;

    @SerializedName("MKTCAP")
    private String marketCap;

    @SerializedName("VOLUME24HOUR")
    private String volume24Hour;


    @SerializedName("SUPPLY")
    private String supply;

    @SerializedName("OPENDAY")
    private String openDay;

    @SerializedName("FROMSYMBOL")
    private String symbol;




    private List<String> historyList;


    public CoinDetail(){}

    public CoinDetail(String price,String high,String low,String changePercent,String marketCap,String volume24Hour){
        this.price=price;
        this.high=high;
        this.low=low;
        this.changePercent=changePercent;
        this.marketCap=marketCap;
        this.volume24Hour=volume24Hour;
    }

    public List<String> getHistoryList() {
        return historyList;
    }

    public String getPrice() {
        return price;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public String getVolume24Hour() {
        return volume24Hour;
    }

    public String getOpenDay() {
        return openDay;
    }

    public String getSupply() {
        return supply;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public void setVolume24Hour(String volume24Hour) {
        this.volume24Hour = volume24Hour;
    }

    public void setHistoryList(List<String> historyList) {
        this.historyList = historyList;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
