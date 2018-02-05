package com.xiongxh.cryptocoin.sync;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import com.xiongxh.cryptocoin.data.CoinDbContract;
import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;
import com.xiongxh.cryptocoin.model.Coin;
import com.xiongxh.cryptocoin.utilities.CoinJsonUtils;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;
import com.xiongxh.cryptocoin.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class CoinTaskService extends GcmTaskService {

    private static final String[] POP_COIN_SYMBOLS = {"BTC", "ETH", "XRP", "BCH", "ADA", "TRX", "EOS", "LTC", "MTL", "CHAT", "BCD"};

    private OkHttpClient client = new OkHttpClient();

    private Context mContext;
    private boolean isUpdate;

    private List<String> coinSymbols = new ArrayList<>();

    public CoinTaskService() {}

    public CoinTaskService(Context context) {
        mContext = context;
    }

    public String fetchData(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        Cursor initQueryCursor;

        if (mContext == null){
            mContext = this;
        }

        Timber.d("Entering onRunTask() method, params tag: " + taskParams.getTag());

        initQueryCursor = mContext.getContentResolver()
                .query(CoinEntry.CONTENT_URI, ConstantsUtils.COIN_COLUMNS, null, null, null);

        if (initQueryCursor == null || initQueryCursor.getCount() == 0){
            Timber.d("before clear, number of coins: " + coinSymbols.size());
            coinSymbols.clear();
            coinSymbols.addAll(Arrays.asList(POP_COIN_SYMBOLS));
            Timber.d("after adding, number of coins: " + coinSymbols.size());
        }else{
            DatabaseUtils.dumpCursor(initQueryCursor);
            initQueryCursor.moveToFirst();
            for (int i = 0; i < initQueryCursor.getCount(); i++){
                coinSymbols.add(initQueryCursor.getString(initQueryCursor.getColumnIndex("symbol")));
                initQueryCursor.moveToNext();
            }
        }

        if (taskParams.getTag().equals("add")){
            Timber.d("To add the symbol to list, current number of coins: " + coinSymbols.size());
            isUpdate = false;

            String toAddCoin = taskParams.getExtras().getString("symbol");

            coinSymbols.add(0, toAddCoin);

            Timber.d(toAddCoin + "is added: " + "current number of coins: " + coinSymbols.size());
//            Set<String> hs = new HashSet<>();
//            hs.addAll(coinSymbols);
//            coinSymbols.clear();
//            coinSymbols.addAll(hs);
        }

        int result = GcmNetworkManager.RESULT_FAILURE;

        try {
            String coinsJsonStr = CoinJsonUtils.loadCoins(mContext);

            //Timber.d("First 500 chars of coins json string: " + coinsJsonStr.substring(0, 500));

            //List<Coin> coins = new ArrayList<Coin>();
            String str = "";

            for (String symbol : coinSymbols) {

                str += symbol + ",";
            }

            String symbolsStr = str.substring(0, str.length()-1);

            URL priceUrl = NetworkUtils.getPriceUrl(symbolsStr);
            String priceJsonStr = fetchData(priceUrl.toString());

            //Timber.d("First 500 chars of price json string: " + priceJsonStr.substring(0, 500));

            if (priceJsonStr != null && !priceJsonStr.isEmpty()){
                Timber.d("result is success!");
                result = GcmNetworkManager.RESULT_SUCCESS;
            }else {
                Timber.d("fetch data failed !");
            }

            List<Coin> coins = CoinJsonUtils.extractCoinsFromJson(coinsJsonStr, priceJsonStr, coinSymbols);

            Timber.d("number of coin objects: " + coins.size());

            ContentValues[] coinValues = CoinJsonUtils.getCoinContentValueFromList(coins);

            //ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();
            if (coinValues != null && coinValues.length != 0){
                Timber.d("coinvalues are not null.");
                Timber.d("number of contentvalues: " + coinValues.length);
//                for (ContentValues value : coinValues){
//                    cpo.add(ContentProviderOperation.newInsert(CoinEntry.CONTENT_URI).withValues(value).build());
//                }
//                ContentResolver coinContentResolver = mContext.getContentResolver();
//                coinContentResolver.applyBatch(CoinDbContract.CONTENT_AUTHORITY, cpo);

//                for (ContentValues value : coinValues){
//                    Timber.d("Inserting coin symbol: " + value.getAsString(CoinEntry.COLUMN_SYMBOL));
//
//                    ContentResolver coinContentResolver = mContext.getContentResolver();
//                    coinContentResolver.insert(CoinEntry.CONTENT_URI, value);
//                }
                for (int i = 0; i < coinValues.length; i++){
                    Timber.d("count for loop: " + i);
                    Timber.d("Inserting coin symbol: " + coinValues[i].getAsString(CoinEntry.COLUMN_SYMBOL));

                    ContentResolver coinContentResolver = mContext.getContentResolver();
                    coinContentResolver.insert(CoinEntry.CONTENT_URI, coinValues[i]);
                }
            }

            //    synchronized public static void syncSelectedCoin(Context context, String symbol){
//        try {
//            String coinsJasonStr = CoinJsonUtils.loadCoins(context);
//            Coin coin = CoinJsonUtils.extractCoinFromJson(coinsJasonStr, symbol);
//
//            ContentValues coinValue = CoinJsonUtils.getContentValueFromCoin(context, coin);
//
//            if (coinValue != null ){
//                ContentResolver coinContentResolver = context.getContentResolver();
//
//                coinContentResolver.insert(CoinEntry.CONTENT_URI, coinValue);
//            }
//        }catch (Exception e){
//            e.getStackTrace();
//        }
//    }
        }catch (Exception e){
            e.getStackTrace();
        }

        return result;
    }
}
