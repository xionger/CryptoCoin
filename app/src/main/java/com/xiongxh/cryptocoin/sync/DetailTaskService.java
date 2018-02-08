package com.xiongxh.cryptocoin.sync;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.xiongxh.cryptocoin.model.Coin;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;
import com.xiongxh.cryptocoin.utilities.NetworkUtils;
import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class DetailTaskService extends GcmTaskService {

    public static final String pref_interval = "histohour";

    private OkHttpClient client = new OkHttpClient();

    private Context mContext;
    public DetailTaskService() {
    }

    public DetailTaskService(Context context) {
        mContext = context;
    }

    public String fetchData(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        if (mContext == null){
            mContext = this;
        }

        String symbol = taskParams.getExtras().getString("symbol");

        int result = GcmNetworkManager.RESULT_FAILURE;

        if (symbol != null && !symbol.isEmpty()){

            try {
                URL histoUrl = NetworkUtils.getHistoUrl(symbol);
                String histoJsonStr = fetchData(histoUrl.toString());

                URL newsUrl = NetworkUtils.getNewsUrl(symbol);
                String newsJsonStr = fetchData(newsUrl.toString());

                //Timber.d("First 500 of histoJsonStr: " + histoJsonStr.substring(0, 500));
                if (histoJsonStr != null && !histoJsonStr.isEmpty() && newsJsonStr != null && !newsJsonStr.isEmpty()){
                //if (histoJsonStr != null && !histoJsonStr.isEmpty() ){
                    Timber.d("result is success!");
                    result = GcmNetworkManager.RESULT_SUCCESS;

//                    Cursor c = mContext.getContentResolver()
//                            .query(CoinEntry.CONTENT_URI,
//                                    //new String[]{CoinEntry.COLUMN_SYMBOL},
//                                    ConstantsUtils.COIN_COLUMNS,
//                                    CoinEntry.COLUMN_SYMBOL + "= ?",
//                                    new String[]{symbol},
//                                    null);

//                    initQueryCursor = mContext.getContentResolver()
//                            .query(CoinEntry.CONTENT_URI, ConstantsUtils.COIN_COLUMNS, null, null, null);

                    //if (c != null && c.moveToFirst()) {
                        //Timber.d("c is not null");

                        //Timber.d("histo before updating: " + c.getString(ConstantsUtils.POSITION_HISTO));

                        ContentValues value = new ContentValues();
//                        value.put(CoinEntry._ID, c.getString(ConstantsUtils.POSITION_ID));
//                        value.put(CoinEntry.COLUMN_SYMBOL, c.getString(ConstantsUtils.POSITION_SYMBOL));
//                        value.put(CoinEntry.COLUMN_NAME, c.getString(ConstantsUtils.POSITION_NAME));
//                        value.put(CoinEntry.COLUMN_COIN_URL, c.getString(ConstantsUtils.POSITION_COIN_URL));
//                        value.put(CoinEntry.COLUMN_IMAGE_URL, c.getString(ConstantsUtils.POSITION_IMAGE_URL));
//                        value.put(CoinEntry.COLUMN_ALGORITHM, c.getString(ConstantsUtils.POSITION_ALGORITHM));
//                        value.put(CoinEntry.COLUMN_PROOF_TYPE, c.getString(ConstantsUtils.POSITION_PROOF_TYPE));
//                        value.put(CoinEntry.COLUMN_TOTAL_SUPPLY, c.getString(ConstantsUtils.POSITION_TOTAL_SUPPLY));
//                        value.put(CoinEntry.COLUMN_SPONSOR, c.getString(ConstantsUtils.POSITION_SPONSOR));
//                        value.put(CoinEntry.COLUMN_SUPPLY, c.getString(ConstantsUtils.POSITION_SUPPLY));
//                        value.put(CoinEntry.COLUMN_PRICE, c.getString(ConstantsUtils.POSITION_PRICE));
//                        value.put(CoinEntry.COLUMN_MKTCAP, c.getString(ConstantsUtils.POSITION_MKTCAP));
//                        value.put(CoinEntry.COLUMN_VOL24H, c.getString(ConstantsUtils.POSITION_VOL24H));
//                        value.put(CoinEntry.COLUMN_VOL24H2, c.getString(ConstantsUtils.POSITION_VOL24H2));
//                        value.put(CoinEntry.COLUMN_OPEN24H, c.getString(ConstantsUtils.POSITION_OPEN24H));
//                        value.put(CoinEntry.COLUMN_HIGH24H, c.getString(ConstantsUtils.POSITION_HIGH24H));
//                        value.put(CoinEntry.COLUMN_LOW24H, c.getString(ConstantsUtils.POSITION_LOW24H));
//                        value.put(CoinEntry.COLUMN_TREND, c.getString(ConstantsUtils.POSITION_TREND));
//                        value.put(CoinEntry.COLUMN_CHANGE, c.getString(ConstantsUtils.POSITION_CHANGE));

                        value.put(CoinEntry.COLUMN_HISTO, histoJsonStr);
                        value.put(CoinEntry.COLUMN_NEWS, newsJsonStr);

                        ContentResolver coinContentResolver = mContext.getContentResolver();
                        coinContentResolver.update(CoinEntry.CONTENT_URI,
                                value,
                                CoinEntry.COLUMN_SYMBOL + "=?",
                                new String[]{symbol}
                                );

//                        Cursor h = mContext.getContentResolver()
//                                .query(CoinEntry.CONTENT_URI,
//                                        ConstantsUtils.COIN_COLUMNS,
//                                        CoinEntry.COLUMN_SYMBOL + "= ?",
//                                        new String[]{symbol},
//                                        null);
//                        if (h != null && h.moveToFirst()) {
//                            String histo = h.getString(ConstantsUtils.POSITION_HISTO);
//                            int len = histo.length();
//
//                            Timber.d("length is: " + len + "histo after updating: " + histo.substring(len-500, len-1));
//                        }

                }else {
                    Timber.d("fetch data failed !");
                }

            }catch (Exception e){
                Timber.d(e.getMessage());
                e.getStackTrace();
            }
        }

        return result;
    }
}
