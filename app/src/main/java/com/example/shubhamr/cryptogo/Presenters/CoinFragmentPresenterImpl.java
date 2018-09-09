package com.example.shubhamr.cryptogo.Presenters;

import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;

import java.util.List;

public class CoinFragmentPresenterImpl implements Contract.CoinFragmentPresenter,Contract.OnFinishedCoinIndex {

    private Contract.CoinFragmentView coinFragmentView;
    private Contract.CryptoCompareAPI cryptoCompareAPI;



   public CoinFragmentPresenterImpl(Contract.CoinFragmentView coinFragmentView,Contract.CryptoCompareAPI cryptoCompareAPI){
       this.coinFragmentView= coinFragmentView;
       this.cryptoCompareAPI = cryptoCompareAPI;
   }




    @Override
    public void getCoin() {

       coinFragmentView.showProgressBar();
       cryptoCompareAPI.getCoinIndex(this);
    }

    @Override
    public void onFinishedCoinList(List<Coin> list) {
          coinFragmentView.hideProgressBar();


          // If there is some error occur while retrieving list show error on main view
          if(list==null){
              coinFragmentView.showError();
          }

          // If operation was successful then pass list to main view for updating
          else{
              coinFragmentView.updateCoinRecyclerView(list);
          }

    }


}
