package com.example.shubhamr.cryptogo.Interface;

import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinDetail;

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


    public interface CoinDetailFragmentView{

        void updateDetails(CoinDetail coinDetail);
        void showError();

    }



    //-------------------------------------


    //----------------------------> PRESENTER

    public interface CoinFragmentPresenter{

        void getCoin();

    }


    public interface CoinDetailFragmentPresenter{



        void getCoinDetail(String symbol);


    }


    //-------------------------------------



    //----------------------------> MODEL


    public interface CryptoCompareAPI {

        void getCoinIndex(OnFinishedCoinIndex listener);

       /* These two method work in same order they are written

          1. Call to getCoinFullData is made which retrieve all detail of coins

          2. After successfully retrieving detail of coin getCoinFullData() will make call to
             getCoinHistory7Day() and will pass the retrieved detail to it.

          3. After successfully retrieving history all detail required will be successfully retrieved then
             only call to presenter will be made.

          Note: If first method will be called the it is sur that second will be called too we can't
                make call to only single one both are joined and dependent on each other for full detail.

       */

        void getCoinFullData(OnFinishedCoinData listener,String symbol);
        void getCoinHistory7Day(OnFinishedCoinData listener,CoinDetail coinDetail,String symbol);


    }



    //-------------------------------------


    //----------------------------> Task Finished Listener

    public interface OnFinishedCoinIndex{
        void onFinishedCoinList(List<Coin> list);
    }


    public interface OnFinishedCoinData{
        void onFinishedCoinDetail(CoinDetail coinDetail);
    }





}
