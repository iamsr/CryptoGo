package com.example.shubhamr.cryptogo.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Coin {

    @SerializedName("ImageUrl")
    private String icon;

    @SerializedName("CoinName")
    private String name;


    @SerializedName("Symbol")
    private String symbol;

    @SerializedName("SortOrder")
    private String ranking;

    public Coin(){ }



    public Coin(String name,String icon,String symbol,String ranking){
        this.name=name;
        this.icon=icon;
        this.symbol = symbol;
        this.ranking=ranking;
    }




    public String getSymbol() {
        return symbol;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getRanking() {
        return ranking;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

}
