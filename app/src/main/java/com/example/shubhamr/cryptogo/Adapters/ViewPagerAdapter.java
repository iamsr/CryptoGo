package com.example.shubhamr.cryptogo.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shubhamr.cryptogo.Views.Fragments.CoinFragment;
import com.example.shubhamr.cryptogo.Views.Fragments.CompareFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int tabNum;
    private Fragment fragment;


    public ViewPagerAdapter(FragmentManager fm, int tabNum) {
        super(fm);
        this.tabNum = tabNum;
    }


    @Override
    public Fragment getItem(int position) {


        // Set fragment according to selected tab position

        switch (position)
        {
            case 1:
                fragment = new CoinFragment();
                break;

            case 2:
                fragment = new CompareFragment();
                break;
        }

        return fragment;

    }




    @Override
    public int getCount() {
        return tabNum;
    }
}
