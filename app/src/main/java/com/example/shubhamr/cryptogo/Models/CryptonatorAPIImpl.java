package com.example.shubhamr.cryptogo.Models;

import android.content.Context;

import com.example.shubhamr.cryptogo.Interface.Contract;

public class CryptonatorAPIImpl implements Contract.CryptonatorAPI {

    private Context context;


    public CryptonatorAPIImpl(Context context){
        this.context = context;
    }



    @Override
    public void getCoinIndex() {

    }
}
