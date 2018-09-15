package com.example.shubhamr.cryptogo.Views.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.Models.CryptoCompareAPIImpl;
import com.example.shubhamr.cryptogo.Presenters.MultiChartFragmentPresenterImpl;
import com.example.shubhamr.cryptogo.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiChartFragment extends Fragment implements Contract.MultiChartFragmentView {

    @BindView(R.id.multiChart) LineChart lineChart;

    private Contract.MultiChartFragmentPresenter multiChartFragmentPresenter;

    private String CHART_TYPE, COIN1, COIN2;


    public MultiChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multi_chart, container, false);
        ButterKnife.bind(this,view);

        // Setting presenter
        multiChartFragmentPresenter = new MultiChartFragmentPresenterImpl(this,new CryptoCompareAPIImpl(getContext()));

        //Retrieving arguments
        if(getArguments()!=null) {
            CHART_TYPE = getArguments().getString("CHART_TYPE");
            COIN1 = getArguments().getString("COIN1");
            COIN2 = getArguments().getString("COIN2");


            // Calling presenter for history data
            multiChartFragmentPresenter.getHistory(COIN1, COIN2, CHART_TYPE);
        }


        return view;
    }







    @Override
    public void setHistoryChart(List<String> coin1List, List<String> coin2List) {

        // Entries for chart
        List<Entry> entries1 = new ArrayList<Entry>();
        List<Entry> entries2 = new ArrayList<Entry>();

        // Putting value in entry object of respectiv coins

        // Coin1
        for(int i=0;i<7;i++){
            entries1.add(new Entry(i+1,(int)Double.parseDouble(coin1List.get(i))));
        }

        // Coin2
        for(int i=0;i<7;i++){
            entries2.add(new Entry(i+1,(int)Double.parseDouble(coin2List.get(i))));
        }


        // Creating data set for both coins
        LineDataSet dataSet1 = new LineDataSet(entries1, COIN1);
        LineDataSet dataSet2 = new LineDataSet(entries2, COIN2);

        /*

             Customization for Line Chart

         */

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


        // Data set 1
        dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet1.setValueTextSize(8);
        dataSet1.setColor(Color.parseColor("#f86053"));
        dataSet1.setCircleColor(Color.parseColor("#fe5e2a"));

        // Data set 2
        dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet2.setValueTextSize(8);
        dataSet2.setColor(Color.parseColor("#03a9f4"));
        dataSet2.setCircleColor(Color.parseColor("#01579b"));




        // Creating Line data and adding both data set
        LineData lineData = new LineData();
        lineData.addDataSet(dataSet1);
        lineData.addDataSet(dataSet2);




        //Setting data into chart
        lineChart.setData(lineData);


        //Description
        lineChart.getDescription().setText("");


        //Refreshing chart
        lineChart.invalidate();








    }

    @Override
    public void showError() {

    }
}
