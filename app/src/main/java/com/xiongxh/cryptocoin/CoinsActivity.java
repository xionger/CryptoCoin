package com.xiongxh.cryptocoin;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiongxh.cryptocoin.data.CoinLoader;
import com.xiongxh.cryptocoin.sync.CoinSyncIntentService;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;

import java.util.Random;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CoinsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 0;
    private Intent mServiceIntent;
    private Context mContext;
    private Cursor mCursor;
    private boolean isConnected = false;
    private RecyclerView mCoinsRecyclerView;
    private CoinAdapter mCoinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());

        Timber.d("onCreate ...");
        mContext = this;

        setContentView(R.layout.activity_coins);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (savedInstanceState == null){
            refresh();
        }

        mCoinsRecyclerView = (RecyclerView) findViewById(R.id.rv_list_coin);

        mCoinAdapter = new CoinAdapter(this, null);
        mCoinsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getLoaderManager().initLoader(LOADER_ID, null, this);

        mCoinsRecyclerView.setAdapter(mCoinAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coins, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        Timber.d("Refreshing data ...");
        Intent serviceIntent = new Intent(this, CoinSyncIntentService.class);
        serviceIntent.putExtra("tag", "init");
        startService(serviceIntent);
    }

    public static boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null)
                if(networkInfo.isConnected())
                    return true;
        }
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Timber.d("Loading data ......");
        return CoinLoader.newAllCoinsInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Timber.d("Loading finished...");
//        CoinAdapter coinAdapter = new CoinAdapter(cursor);
//        coinAdapter.setHasStableIds(true);
//        mCoinsRecyclerView.setAdapter(coinAdapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mCoinsRecyclerView.setLayoutManager(layoutManager);
        mCoinAdapter.swapCursor(cursor);
        mCursor = cursor;

        View view = findViewById(R.id.tv_empty_loading);

        if (mCursor == null || mCursor.getCount() == 0){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.INVISIBLE);
        }

        final Snackbar snackbar = Snackbar.make(mCoinsRecyclerView, "Load Finished", Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(Color.MAGENTA)
                .setAction("Refresh", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        snackbar.show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCoinsRecyclerView.setAdapter(null);
    }

//    private class CoinAdapter extends RecyclerView.Adapter<CoinViewHolder>{
//        private Cursor mCursor;
//
//        public CoinAdapter(Cursor cursor){
//            mCursor = cursor;
//        }
//
//        @Override
//        public long getItemId(int position){
//            mCursor.moveToPosition(position);
//            return mCursor.getLong(ConstantsUtils.POSITION_ID);
//        }
//
//        @Override
//        public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_coin_basic, parent, false);
//
//            final  CoinViewHolder viewHolder = new CoinViewHolder(view);
//
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(CoinViewHolder holder, int position) {
//            if (!mCursor.moveToFirst()){
//                return;
//            }
//
//            mCursor.moveToPosition(position);
//
//            String coinSymbol = mCursor.getString(ConstantsUtils.POSITION_SYMBOL);
//            holder.mSymbolTextView.setText(coinSymbol);
//
//            /////////////////////////////////////////////////////////////////////
//            //Fake price data below
//            Random rand = new Random();
//
//            int price = rand.nextInt(10000) + 1;
//
//            int change = 100 - rand.nextInt(200);
//            //End of fake price data
//            ////////////////////////////////////////////////////////////////////
//            holder.mPriceTextView.setText("$" + price);
//            holder.mChangeTextView.setText(change + "%");
//        }
//
//        @Override
//        public int getItemCount() {
//            return mCursor.getCount();
//        }
//    }
//
//    public static class CoinViewHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.symbol)
//        TextView mSymbolTextView;
//        @BindView(R.id.price)
//        TextView mPriceTextView;
//        @BindView(R.id.change)
//        TextView mChangeTextView;
//
//        public CoinViewHolder(View itemView) {
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//        }
//    }
}
