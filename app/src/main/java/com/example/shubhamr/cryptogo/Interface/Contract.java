package com.example.shubhamr.cryptogo.Interface;

import com.example.shubhamr.cryptogo.ModelClasses.Coin;

import java.util.List;

public class Contract {

    //----------------------------> VIEW



    // Methods must present in every view

    public interface BaseView{
         void showProgressBar();
         void hideProgressBar();
         void showError();
    }


    public interface CoinFragmentView extends BaseView{

        void updateCoinRecyclerView(List<Coin> coinList);

    }



    //-------------------------------------


    //----------------------------> PRESENTER

    public interface CoinFragmentPresenter{

        void getCoin();

    }


    //-------------------------------------



    //----------------------------> MODEL


    public interface CryptoCompareAPI {

        void getCoinIndex(OnFinishedCoinIndex listener);

    }



    //-------------------------------------


    //----------------------------> Task Finished Listener

    public interface OnFinishedCoinIndex{
        void onFinishedCoinList(List<Coin> list);
    }





}
