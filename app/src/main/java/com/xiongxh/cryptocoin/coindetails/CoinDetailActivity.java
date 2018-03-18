package com.xiongxh.cryptocoin.coindetails;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.Toast;

import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.sync.DetailIntentService;
import com.xiongxh.cryptocoin.utilities.NetworkUtils;

import timber.log.Timber;

public class CoinDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String mSymbol;

    private Intent mDetailServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSymbol = getIntent().getExtras().getString(getString(R.string.symbol_tag_capital));
        String name = getIntent().getExtras().getString(getString(R.string.name_tag_capital));

        mDetailServiceIntent = new Intent(this, DetailIntentService.class);

        if (NetworkUtils.isNetworkStatusAvailable(this)) {
            refreshDetail(mSymbol);
        }else {
            Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT).show();
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        SectionPagerAdapter sectionPagerAdapter =
                new SectionPagerAdapter(getSupportFragmentManager(), mSymbol);

        mViewPager.setAdapter(sectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        getSupportActionBar().setTitle(name);

    }

    private void refreshDetail(String symbol) {
        //Timber.d("Refreshing data ...");
        mDetailServiceIntent.putExtra(getString(R.string.tag_tag), getString(R.string.tag_detail_value));
        mDetailServiceIntent.putExtra(getString(R.string.symbol_tag), symbol);
        startService(mDetailServiceIntent);
    }
}
