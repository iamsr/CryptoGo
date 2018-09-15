package com.example.shubhamr.cryptogo.Presenters;

import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinDetail;

import java.util.ArrayList;
import java.util.List;

public class CompareFragmentPresenterImpl implements Contract.CompareFragmentPresenter,Contract.OnFinishedCoinCompare,Contract.OnFinishedCoinIndex {

    private Contract.CompareFragmentView compareFragmentView;
    private Contract.CryptoCompareAPI cryptoCompareAPI;
    private CoinDetail coin1,coin2;

    public CompareFragmentPresenterImpl(Contract.CompareFragmentView compareFragmentView,Contract.CryptoCompareAPI cryptoCompareAPI){
        this.compareFragmentView=compareFragmentView;
        this.cryptoCompareAPI=cryptoCompareAPI;
    }



    @Override
    public void getCoinList() {
        compareFragmentView.showProgressBar();
        cryptoCompareAPI.getCoinIndex(this);

    }


    // Taking out names from list and making new string list for spinner

    @Override
    public void getSpinnerList(List<Coin> coinList) {

        List<String> nameList = new ArrayList<String>();
        for(int i=0;i<coinList.size();i++){
            nameList.add(coinList.get(i).getName());
        }

        compareFragmentView.setSpinner(nameList);
    }

    @Override
    public void getBothCoinDetail(Coin coin1, Coin coin2) {
        // COin1 detail
        cryptoCompareAPI.getBothCoinDetails(this,coin1,1);

        //Coin2 detail
        cryptoCompareAPI.getBothCoinDetails(this,coin2,2);

    }


    // When Retrieving coin info list completed
    @Override
    public void onFinishedCoinList(List<Coin> coinList) {
        compareFragmentView.hideProgressBar();

        if(coinList==null){
            compareFragmentView.showError();

        }

        else{
            compareFragmentView.setCoinList(coinList);
        }
    }




    @Override
    public void onFinishedCoinDetails(CoinDetail coinDetail, int coinNum) {

        // If error occurred show error on screen
        if(coinDetail==null){
            compareFragmentView.showError();
        }

        // Set coin according to number
        switch (coinNum)
        {
            case 1 :
                coin1 = coinDetail;
                break;

            case 2 :
                coin2 = coinDetail;
                break;
            default:
                coin1=null;
                coin2=null;
        }

        if(coin1!=null&&coin2!=null){
            compareFragmentView.setCoinsDetail(coin1,coin2);
        }

    }
}
;