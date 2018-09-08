package com.example.shubhamr.cryptogo.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubhamr.cryptogo.Adapters.CoinsRecyclerViewAdapter;
import com.example.shubhamr.cryptogo.Interface.ClickListenerInterface;
import com.example.shubhamr.cryptogo.Interface.Contract;
import com.example.shubhamr.cryptogo.ModelClasses.Coin;
import com.example.shubhamr.cryptogo.Models.CryptonatorAPIImpl;
import com.example.shubhamr.cryptogo.Presenters.CoinFragmentPresenterImpl;
import com.example.shubhamr.cryptogo.R;

import java.util.List;

import butterknife.BindView;


public class CoinFragment extends Fragment implements ClickListenerInterface,Contract.CoinFragmentView {

    @BindView(R.id.coinsRecyclerView)RecyclerView coinRecyclerView;


    private CoinsRecyclerViewAdapter coinsRecyclerViewAdapter;
    private Contract.CoinFragmentPresenter coinFragmentPresenter;


    public CoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_coin, container, false);


        // Presenter Instance (For communication with models)

        coinFragmentPresenter = new CoinFragmentPresenterImpl(this,new CryptonatorAPIImpl(getContext()));


        return view;
    }





    @Override
    public void itemClicked(View view, int position) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void updateCoinRecyclerView(List<Coin> coinList) {

    }
}
