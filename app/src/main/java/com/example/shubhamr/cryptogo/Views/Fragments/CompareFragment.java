package com.example.shubhamr.cryptogo.Views.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shubhamr.cryptogo.Adapters.CompareViewPagerAdapter;
import com.example.shubhamr.cryptogo.Adapters.ViewPagerAdapter;
import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.ModelClasses.CoinDetail;
import com.example.shubhamr.cryptogo.Models.CryptoCompareAPIImpl;
import com.example.shubhamr.cryptogo.Presenters.CompareFragmentPresenterImpl;
import com.example.shubhamr.cryptogo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompareFragment extends Fragment implements AdapterView.OnItemSelectedListener, Contract.CompareFragmentView {

    @BindView(R.id.spinner1)Spinner spinnerCoin1;
    @BindView(R.id.spinner2)Spinner spinnerCoin2;
    @BindView(R.id.compareProgressBar)ProgressBar progressBar;
    @BindView(R.id.chartViewPager)ViewPager chartViewPager;
    @BindView(R.id.chartTabLayout)TabLayout chartTabLayout;
    @BindView(R.id.chartCardView)CardView chartCardView;
    @BindView(R.id.compareDetailContainer)View compareContainer;
    @BindView(R.id.compareErrorIcon)ImageView errorIcon;
    @BindView(R.id.compareRetryButton)Button retryButton;

    @BindView(R.id.coin1Change)TextView coin1Change;
    @BindView(R.id.coin2Change)TextView coin2Change;
    @BindView(R.id.coin1Price)TextView coin1Price;
    @BindView(R.id.coin2Price)TextView coin2Price;
    @BindView(R.id.coin1Market)TextView coin1Market;
    @BindView(R.id.coin2Market)TextView coin2Market;
    @BindView(R.id.coin1Supply)TextView coin1Supply;
    @BindView(R.id.coin2Supply)TextView coin2Supply;
    @BindView(R.id.coin1Volume)TextView coin1Volume;
    @BindView(R.id.coin2Volume)TextView coin2Volume;
    @BindView(R.id.coin1High)TextView coin1High;
    @BindView(R.id.coin2High)TextView coin2High;
    @BindView(R.id.coin1Low)TextView coin1Low;
    @BindView(R.id.coin2Low)TextView coin2Low;
    @BindView(R.id.coin1Open)TextView coin1Open;
    @BindView(R.id.coin2Open)TextView coin2Open;



    private Contract.CompareFragmentPresenter compareFragmentPresenter;
    private List<Coin> coinList;
    private int spinner1Pos=-1,spinner2Pos=-1;
    private CompareViewPagerAdapter compareViewPagerAdapter;


    public CompareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compare, container, false);
        ButterKnife.bind(this,view);

       //Set Up View Pager
        setViewPager();

        // Presenter
        compareFragmentPresenter = new CompareFragmentPresenterImpl(this,new CryptoCompareAPIImpl(getContext()));

        // Get data for spinner
        compareFragmentPresenter.getCoinList();

        return view;
    }



    public void setViewPager(){

        compareViewPagerAdapter = new CompareViewPagerAdapter(getChildFragmentManager(),chartTabLayout.getTabCount());
        chartViewPager.setAdapter(compareViewPagerAdapter);

        // addOnPageChangeListener event change the tab on slide
        chartViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(chartTabLayout));

        // When tab is selected manually
        chartTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                chartViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    // Spinner methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Checks which spinner item selected and set global position variable accordingly

        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

        if(parent.getId()==R.id.spinner1){
            spinner1Pos = position;
        }
        else{
           spinner2Pos = position;
        }

        // Runs when there is something selected in both spinners (If one is still not selected nothing will happen)
        if(spinner1Pos!=-1&&spinner2Pos!=-1){

            // Get both selected coin details
           compareFragmentPresenter.getBothCoinDetail(coinList.get(spinner1Pos),coinList.get(spinner2Pos));
            //Calling view pager adapter for chart changes
            Bundle bundle = new Bundle();
            bundle.putString("COIN1", coinList.get(spinner1Pos).getSymbol());
            bundle.putString("COIN2",coinList.get(spinner2Pos).getSymbol());

            // Set new data for fragment and notify view pager about change
            compareViewPagerAdapter.setBundle(bundle);
            compareViewPagerAdapter.notifyDataSetChanged();

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Setting spinner data
    @Override
    public void setSpinner(List<String> nameList) {

     // Setting listener for spinner
       spinnerCoin1.setOnItemSelectedListener(this);
       spinnerCoin2.setOnItemSelectedListener(this);

    // Adapter for spinner
       ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, nameList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Attaching adapter with spinner
       spinnerCoin1.setAdapter(dataAdapter);
       spinnerCoin2.setAdapter(dataAdapter);

    }



    // Coin List with name, symbol etc
    @Override
    public void setCoinList(List<Coin> coinList) {
        this.coinList=coinList;

        // List retrieved from network now showing all views
        spinnerCoin1.setVisibility(View.VISIBLE);
        spinnerCoin2.setVisibility(View.VISIBLE);


        // Retrieving string list for spinner (Taking out names only from original list)
        compareFragmentPresenter.getSpinnerList(coinList);
    }




    // Set values in detail layout of coins
    @Override
    public void setCoinsDetail(CoinDetail coin1,CoinDetail coin2) {


        chartCardView.setVisibility(View.VISIBLE);
        compareContainer.setVisibility(View.VISIBLE);



        // Change
        coin1Change.setText(coin1.getChangePercent()+" %");
        coin2Change.setText(coin2.getChangePercent()+" %");

        // Price
        coin1Price.setText(coin1.getPrice());
        coin2Price.setText(coin2.getPrice());

        //Market
        coin1Market.setText(coin1.getMarketCap());
        coin2Market.setText(coin2.getMarketCap());

        //Supply
        coin1Supply.setText(coin1.getSupply());
        coin2Supply.setText(coin2.getSupply());

        //Volume
        coin1Volume.setText(coin1.getVolume24Hour());
        coin2Volume.setText(coin2.getVolume24Hour());

        //High
        coin1High.setText(coin1.getHigh());
        coin2High.setText(coin2.getHigh());

        //Low
        coin1Low.setText(coin1.getLow());
        coin2Low.setText(coin2.getLow());

        //Open
        coin1Open.setText(coin1.getOpenDay());
        coin2Open.setText(coin2.getOpenDay());




    }


    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {

        // Showing error views
        errorIcon.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);

        //Hiding all views
        spinnerCoin1.setVisibility(View.GONE);
        spinnerCoin2.setVisibility(View.GONE);
        chartCardView.setVisibility(View.GONE);
        compareContainer.setVisibility(View.GONE);

    }

    @OnClick(R.id.compareRetryButton)
    public void onRetry(){

        errorIcon.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        compareFragmentPresenter.getCoinList();
    }

}
