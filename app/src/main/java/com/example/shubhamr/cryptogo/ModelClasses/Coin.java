package com.example.shubhamr.cryptogo.ModelClasses;

public class Coin {

    private String icon;
    private String name;
    private String price;
    private String changePrice;
    private String volume;

    public Coin(){ }


    // For coin index recycler view purpose
    public Coin(String name,String price,String changePrice){
        this.name=name;
        this.price=price;
        this.changePrice=changePrice;
        this.volume=volume;
    }

    // For coin chart purpose
    public Coin(String name,String price,String changePrice,String volume){
        this.name=name;
        this.price=price;
        this.changePrice=changePrice;
        this.volume=volume;
    }


    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getChangePrice() {
        return changePrice;
    }

    public String getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
