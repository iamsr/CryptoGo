package com.example.shubhamr.cryptogo.Presenters;

import com.example.shubhamr.cryptogo.Interface.Contract;

public class CoinFragmentPresenterImpl implements Contract.CoinFragmentPresenter,Contract.OnFinishedCoinIndex {

    private Contract.CoinFragmentView coinFragmentView;
    private Contract.CryptonatorAPI cryptonatorAPI;



   public CoinFragmentPresenterImpl(Contract.CoinFragmentView coinFragmentView,Contract.CryptonatorAPI cryptonatorAPI){
       this.coinFragmentView= coinFragmentView;
       this.cryptonatorAPI = cryptonatorAPI;
   }




    @Override
    public void getCoin() {

    }
}
