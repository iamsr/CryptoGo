package com.example.shubhamr.cryptogo.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shubhamr.cryptogo.Views.Fragments.MultiChartFragment;

public class CompareViewPagerAdapter extends FragmentStatePagerAdapter {

    private int tabNum;
    private Bundle bundle;
    public Fragment fragment;

    public CompareViewPagerAdapter(FragmentManager fm, int tabNum) {
        super(fm);
        this.tabNum = tabNum;
    }




    @Override
    public Fragment getItem(int position) {

        fragment = new MultiChartFragment();

        if(bundle!=null){
        switch (position){

            case 0 :
                bundle.putString("CHART_TYPE","WEEKLY");
                break;

            case 1 :
                bundle.putString("CHART_TYPE","HOURLY");
                break;

            case 2 :
                bundle.putString("CHART_TYPE","MINUTES");
                break;
        }

        fragment.setArguments(bundle);

    }
      return fragment;
    }

    @Override
    public int getCount() {
        return tabNum;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setBundle(Bundle bundle){
        this.bundle =bundle;
    }



}
