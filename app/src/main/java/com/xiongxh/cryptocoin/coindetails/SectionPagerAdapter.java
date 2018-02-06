package com.xiongxh.cryptocoin.coindetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter{

    String mSymbol;
    CoinDetailFragment mCoinDetailFragment;
    HistoFragment mHistoFragment;
    TweetsFragment mTweetsFragment;

    public SectionPagerAdapter(FragmentManager fm, String symbol){
        super(fm);
        this.mSymbol = symbol;
        mCoinDetailFragment = CoinDetailFragment.newInstance(symbol);
        mHistoFragment = HistoFragment.newInstance(symbol);
        mTweetsFragment = TweetsFragment.newInstance(symbol);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mCoinDetailFragment;
            case 1:
                return mHistoFragment;
            case 2:
                return mTweetsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "INFO";
            case 1:
                return "CHARTS";
            case 2:
                return  "TWEETS";
        }
        return null;
    }
}
