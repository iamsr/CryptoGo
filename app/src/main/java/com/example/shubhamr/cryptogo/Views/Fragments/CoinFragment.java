package com.example.shubhamr.cryptogo.Views.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shubhamr.cryptogo.Adapters.CoinsRecyclerViewAdapter;
import com.example.shubhamr.cryptogo.Interface.ClickListenerInterface;
import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.Models.CryptoCompareAPIImpl;
import com.example.shubhamr.cryptogo.Presenters.CoinFragmentPresenterImpl;
import com.example.shubhamr.cryptogo.R;
import com.steelkiwi.library.SlidingSquareLoaderView;
import com.steelkiwi.library.view.SquareView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CoinFragment extends Fragment implements ClickListenerInterface,Contract.CoinFragmentView {

    @BindView(R.id.coinsRecyclerView)RecyclerView coinRecyclerView;
    @BindView(R.id.coinProgressView) ProgressBar progressBar;
    @BindView(R.id.errorIcon)ImageView errorIcon;
    @BindView(R.id.coinRetryButton)Button retryButton;
    @BindView(R.id.coinScroll)ScrollView scrollView;


    private CoinsRecyclerViewAdapter coinsRecyclerViewAdapter;
    private Contract.CoinFragmentPresenter coinFragmentPresenter;
    private View LAST_VIEW;


    public CoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_coin, container, false);
        ButterKnife.bind(this,view);


        // Presenter Instance (For communication with models)

       coinFragmentPresenter = new CoinFragmentPresenterImpl(this,new CryptoCompareAPIImpl(getContext()));
       coinFragmentPresenter.getCoin();

        return view;
    }




    // When coin selected from list
    @Override
    public void itemClicked(View view, int position) {

        if(LAST_VIEW!=null){
            LAST_VIEW.setBackgroundColor(Color.parseColor("#ffffff"));
            LAST_VIEW.setElevation(0);
        }
        Coin coin = coinsRecyclerViewAdapter.getCoinList().get(position);
        coinsRecyclerViewAdapter.setLAST_ITEM_SELECTED(position);
        view.setElevation(10);
        view.setBackgroundColor(Color.parseColor("#fafafa"));
        LAST_VIEW=view;

    //1.    coinRecyclerView.getLayoutManager().scrollToPosition(0);
        scrollView.smoothScrollTo(0,scrollView.getTop());

        //Change Chart
        setChartFragment(coin);


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
    errorIcon.setVisibility(View.VISIBLE);
    retryButton.setVisibility(View.VISIBLE);
    }


    // Update Recycler View for Coin list
    @Override
    public void updateCoinRecyclerView(List<Coin> coinList) {

        //Setting up recycler view and adapter
        coinRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        coinsRecyclerViewAdapter  =new CoinsRecyclerViewAdapter(coinList);
        coinsRecyclerViewAdapter.setClickListeners(this);
        coinRecyclerView.setAdapter(coinsRecyclerViewAdapter);

        //Set first coin chart
        if(coinList!=null){
        setChartFragment(coinList.get(0));}
    }


    // Retry Button
    @OnClick(R.id.coinRetryButton)
    public void onRetry(){
        errorIcon.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        coinFragmentPresenter.getCoin();
    }


    // Set chart in the activity
    public void setChartFragment(Coin coin){

        // Replace chart detail with current selected coin detail
        Fragment fragment = new CoinDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SYMBOL",coin.getSymbol());
        bundle.putString("NAME",coin.getName());
        fragment.setArguments(bundle);
         getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.chartLayout,fragment).commit();
    }

}
