package com.example.shubhamr.cryptogo.Presenters;

import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.CoinDetail;

public class CoinDetailFragmentPresenterImpl implements Contract.CoinDetailFragmentPresenter,Contract.OnFinishedCoinData {

    private Contract.CoinDetailFragmentView coinDetailFragmentView;
    private Contract.CryptoCompareAPI cryptoCompareAPI;

    public CoinDetailFragmentPresenterImpl(Contract.CoinDetailFragmentView coinDetailFragmentView,Contract.CryptoCompareAPI cryptoCompareAPI){

        this.coinDetailFragmentView =coinDetailFragmentView;
        this.cryptoCompareAPI =cryptoCompareAPI;
    }




    @Override
    public void getCoinDetail(String symbol) {

        //Getting Coin Detail Coin detail Layout
        cryptoCompareAPI.getCoinFullData(this,symbol);

    }

    @Override
    public void onFinishedCoinDetail(CoinDetail coinDetail) {

        // If null it means some error occurred show error on screen
        if(coinDetail==null){
            coinDetailFragmentView.showError();
        }

        // Task successful return coin details object
        else{
            coinDetailFragmentView.updateDetails(coinDetail);
        }

    }
}
