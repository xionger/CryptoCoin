package com.xiongxh.cryptocoin.coins;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.coindetails.CoinDetailActivity;
import com.xiongxh.cryptocoin.data.CoinLoader;
import com.xiongxh.cryptocoin.setting.SettingsActivity;
import com.xiongxh.cryptocoin.sync.CoinSyncIntentService;
import com.xiongxh.cryptocoin.sync.CoinTaskService;
import com.xiongxh.cryptocoin.utilities.CoinJsonUtils;
import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;
import com.xiongxh.cryptocoin.utilities.NetworkUtils;

import timber.log.Timber;

public class CoinsActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        CoinAdapter.CoinAdapterOnclickHandler,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 0;
    private Intent mServiceIntent;
    private Context mContext;
    private Cursor mCursor;
    private RecyclerView mCoinsRecyclerView;
    private CoinAdapter mCoinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_coins);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mServiceIntent = new Intent(this, CoinSyncIntentService.class);

        setupSharedPreferences();

        if (savedInstanceState == null){
            if (NetworkUtils.isNetworkStatusAvailable(mContext)) {
                refresh();
            }else {
                networkError();
            }
        }

        mCoinsRecyclerView = (RecyclerView) findViewById(R.id.rv_list_coin);

        mCoinAdapter = new CoinAdapter(this, this);
        mCoinsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        mCoinsRecyclerView.setAdapter(mCoinAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Timber.d("Fab is clicked.");
                if (NetworkUtils.isNetworkStatusAvailable(mContext)) {
                    //Timber.d("Network is connected.");
                    new MaterialDialog.Builder(mContext).title(R.string.search_symbol)
                            .content(R.string.content_test)
                            .backgroundColor(getResources().getColor(R.color.dialog_background))
                            .inputType(InputType.TYPE_CLASS_TEXT)
                            .input(R.string.input_hint, R.string.input_default, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    // On FAB click, receive user input. Make sure the coin doesn't already exist
                                    // in the DB and proceed accordingly
                                    String inputStr = input.toString();
                                    String cleanInput = inputStr.trim().toUpperCase();

                                    //Timber.d("Clean input: " + cleanInput);
                                    Cursor c = getContentResolver()
                                            .query(CoinEntry.CONTENT_URI,
                                                    new String[]{CoinEntry.COLUMN_SYMBOL},
                                                    CoinEntry.COLUMN_SYMBOL + "= ?",
                                                    new String[]{cleanInput},
                                                    null);

                                    if (c.getCount() != 0) {
                                        Toast toast =
                                                Toast.makeText(CoinsActivity.this,
                                                        R.string.error_coin_already_saved,
                                                        Toast.LENGTH_LONG);

                                        toast.setGravity(Gravity.CENTER, Gravity.CENTER, 0);
                                        toast.show();
                                        return;
                                    } else {
                                        if (!CoinJsonUtils.isSymbolValid(mContext, cleanInput)) {
                                            Toast toast =
                                                    Toast.makeText(CoinsActivity.this,
                                                            R.string.error_coin_not_found,
                                                            Toast.LENGTH_LONG);

                                            toast.setGravity(Gravity.CENTER, Gravity.CENTER, 0);
                                            toast.show();
                                            return;
                                        } else {
                                            // Add the Coin to DB
                                            mServiceIntent.putExtra(getString(R.string.tag_tag), getString(R.string.tag_add_value));
                                            mServiceIntent.putExtra(getString(R.string.symbol_tag), cleanInput);
                                            startService(mServiceIntent);
                                            //Timber.d(cleanInput + " is added.");
                                            c.close();
                                        }
                                    }
                                }
                            })
                            .show();
                } else {
                    networkError();
                }

            }
        });

        if(NetworkUtils.isNetworkStatusAvailable(mContext)) {
            long period = 3600L;
            long flex = 10L;
            String periodicTag = getString(R.string.tag_periodic);

            // create a periodic task to pull coins once every hour after the app has been opened. This
            // is so Widget data stays up to date.
            PeriodicTask periodicTask = new PeriodicTask.Builder()
                    .setService(CoinTaskService.class)
                    .setPeriod(period)
                    .setFlex(flex)
                    .setTag(periodicTag)
                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                    .setRequiresCharging(false)
                    .build();

            GcmNetworkManager.getInstance(this).schedule(periodicTask);
        }
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
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        if (id == R.id.action_refresh){
            if (NetworkUtils.isNetworkStatusAvailable(mContext)) {
                refresh();
            }else {
                networkError();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        //Timber.d("Refreshing data ...");
        mServiceIntent.putExtra(getString(R.string.tag_tag), getString(R.string.tag_init));
        startService(mServiceIntent);
    }

    public void networkError(){
        Toast.makeText(mContext, getString(R.string.network_not_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //Timber.d("Loading data ......");
        return CoinLoader.newAllCoinsInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Timber.d("Loading finished...");

        mCoinAdapter.swapCursor(cursor);
        mCursor = cursor;

        View view = findViewById(R.id.tv_empty_loading);
        ProgressBar mProgressbar = findViewById(R.id.pb_list_coin);

        if (mCursor == null || mCursor.getCount() == 0){
            view.setVisibility(View.VISIBLE);
            mProgressbar.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.INVISIBLE);
            mProgressbar.setVisibility(View.INVISIBLE);
        }

        final Snackbar snackbar = Snackbar.make(mCoinsRecyclerView, getString(R.string.load_finished), Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(Color.MAGENTA)
                .setAction(getString(R.string.action_refresh), new View.OnClickListener() {
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

    @Override
    public void onClick(String symbol, String name) {
        final Intent detailIntent = new Intent(this, CoinDetailActivity.class);
        detailIntent.putExtra(getString(R.string.symbol_tag_capital), symbol);
        detailIntent.putExtra(getString(R.string.name_tag_capital), name);
        startActivity(detailIntent);
    }

    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (NetworkUtils.isNetworkStatusAvailable(mContext)) {
            refresh();
        }else {
            networkError();
        }
    }
}
