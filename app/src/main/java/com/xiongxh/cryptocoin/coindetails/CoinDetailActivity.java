package com.xiongxh.cryptocoin.coindetails;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import com.xiongxh.cryptocoin.R;

public class CoinDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String symbol = getIntent().getExtras().getString("SYMBOL");
        String name = getIntent().getExtras().getString("NAME");

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        SectionPagerAdapter sectionPagerAdapter =
                new SectionPagerAdapter(getSupportFragmentManager(), symbol);

        mViewPager.setAdapter(sectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        getSupportActionBar().setTitle(name);

    }
}
