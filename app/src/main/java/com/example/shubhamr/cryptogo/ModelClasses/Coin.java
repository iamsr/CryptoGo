package com.example.shubhamr.cryptogo.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Coin {

    @SerializedName("ImageUrl")
    private String icon;

    @SerializedName("CoinName")
    private String name;


    @SerializedName("Symbol")
    private String symbol;

    public Coin(){ }



    public Coin(String name,String icon,String symbol){
        this.name=name;
        this.icon=icon;
        this.symbol = symbol;
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


    public void setName(String name) {
        this.name = name;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
