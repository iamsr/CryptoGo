package com.example.shubhamr.cryptogo.Views.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.CoinDetail;
import com.example.shubhamr.cryptogo.Models.CryptoCompareAPIImpl;
import com.example.shubhamr.cryptogo.Presenters.CoinDetailFragmentPresenterImpl;
import com.example.shubhamr.cryptogo.R;
import com.example.shubhamr.cryptogo.Views.Activities.MainActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CoinDetailFragment extends Fragment implements Contract.CoinDetailFragmentView {

    @BindView(R.id.price)TextView price;
    @BindView(R.id.marketCapValue)TextView marketCapValue;
    @BindView(R.id.volumeValue)TextView volumeValue;
    @BindView(R.id.highValue)TextView highValue;
    @BindView(R.id.lowValue)TextView lowValue;
    @BindView(R.id.changePercent) TextView changeValue;
    @BindView(R.id.chart)LineChart lineChart;
    @BindView(R.id.coinName)Button coinName;

    public Contract.CoinDetailFragmentPresenter coinDetailFragmentPresenter;
    public String symbol,nameArg;


    public CoinDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coin_detail, container, false);
        ButterKnife.bind(this,view);

        if(getArguments()!=null) {
            // Retrieving type of coin
            symbol = getArguments().getString("SYMBOL");
            nameArg = getArguments().getString("NAME");
        }
        //Presenter
        coinDetailFragmentPresenter= new CoinDetailFragmentPresenterImpl(this,new CryptoCompareAPIImpl(getContext()));

        //Asking for detail of coins
        coinDetailFragmentPresenter.getCoinDetail(symbol);

        return view;
    }



    @Override
    public void updateDetails(CoinDetail coinDetail) {

        // Set change text color accordingly
        if(coinDetail.getChangePercent().charAt(0)=='-'){
            changeValue.setTextColor(Color.parseColor("#f44336"));
            changeValue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sort_down,0,0,0);


        }
        else{
            changeValue.setTextColor(Color.parseColor("#64dd17"));
            changeValue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sort_up,0,0,0);
        }

       // pading for low higih icon
        changeValue.setCompoundDrawablePadding(8);


        //Set text in respected field
        price.setText(coinDetail.getPrice());
        marketCapValue.setText(coinDetail.getMarketCap());
        volumeValue.setText(coinDetail.getVolume24Hour());
        highValue.setText(coinDetail.getHigh());
        lowValue.setText(coinDetail.getLow());
        changeValue.setText(coinDetail.getChangePercent()+"%");
        coinName.setText(nameArg);





        //Set Chart



        // Retrieving history list of coin from its object
        List<String> historyList = coinDetail.getHistoryList();

        // Making List of type Entry(Chart class) which require to parameter(x,y)
        List<Entry> entries = new ArrayList<Entry>();

        // Adding history list item in entry list
        for(int i=0;i<7;i++){
            entries.add(new Entry(i+1,(int)Double.parseDouble(historyList.get(i))));
        }

        // add entries to data set
        LineDataSet dataSet = new LineDataSet(entries, coinDetail.getSymbol());


        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueTextSize(8);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(getResources().getDrawable(R.drawable.graph_gradient));
        dataSet.setColor(Color.parseColor("#f86053"));
        dataSet.setCircleColor(Color.parseColor("#fe5e2a"));





        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);


        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setPinchZoom(true);
        lineChart.animateXY(1000,1000);


        YAxis yAxisL = lineChart.getAxisLeft();
        yAxisL.setEnabled(false);


        YAxis yAxisR = lineChart.getAxisRight();
        yAxisR.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(false);

        //Description
        lineChart.getDescription().setText("");


        lineChart.invalidate(); // refresh



    }

    @Override
    public void showError() {


    }


}
