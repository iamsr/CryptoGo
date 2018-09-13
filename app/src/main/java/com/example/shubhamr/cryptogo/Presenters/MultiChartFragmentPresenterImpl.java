package com.example.shubhamr.cryptogo.Presenters;

import com.example.shubhamr.cryptogo.Interface.Contract;

import java.util.List;

public class MultiChartFragmentPresenterImpl implements Contract.MultiChartFragmentPresenter,Contract.OnFinishedCoinHistory {

    private Contract.MultiChartFragmentView multiChartFragmentView;
    private Contract.CryptoCompareAPI cryptoCompareAPI;

    private List<String> coin1History,coin2History;



    public MultiChartFragmentPresenterImpl(Contract.MultiChartFragmentView multiChartFragmentView,Contract.CryptoCompareAPI cryptoCompareAPI) {

        this.multiChartFragmentView =multiChartFragmentView;
        this.cryptoCompareAPI=cryptoCompareAPI;

    }


    // Retrieve history of coins
    @Override
    public void getHistory(String coin1, String coin2, String type) {

        // Call api for history list  (coin 1, coin 2)
        cryptoCompareAPI.getTwoCoinHistory(this,coin1,type,"1");
        cryptoCompareAPI.getTwoCoinHistory(this,coin2,type,"2");



    }



    // Callback for api
    @Override
    public void onFinishedWeekly(List<String> coinHistory, String coinNum) {

        // If list is null it mean error occured show error on screen
        if(coinHistory==null){
            multiChartFragmentView.showError();
        }

        // Set history list of coin according to number passed which tell for what coin we are requesting list
        switch (coinNum)
        {
            // COIN 1
            case "1" :
                this.coin1History = coinHistory;
                break;

             // COIN 2
            case "2" :
                this.coin2History = coinHistory;
                break;
        }

        // If both list retrieved sent back the result
        if(coin1History!=null&&coin2History!=null){
            multiChartFragmentView.setHistoryChart(coin1History,coin2History);

        }


    }
}
