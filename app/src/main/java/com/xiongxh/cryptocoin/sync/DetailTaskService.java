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

                boolean toUpdate = false;
                long now = System.currentTimeMillis();

                Cursor c = mContext.getContentResolver()
                        .query(CoinEntry.CONTENT_URI,
                                //new String[]{CoinEntry.COLUMN_SYMBOL},
                                ConstantsUtils.COIN_COLUMNS,
                                CoinEntry.COLUMN_SYMBOL + "= ?",
                                new String[]{symbol},
                                null);

                long update = 0;

                if (c != null && c.moveToFirst()) {
                    update = c.getLong(ConstantsUtils.POSITION_UPDATE);
                    Timber.d("update: " + update);
                }

                if (update == 0 ){
                    toUpdate = true;
                    Timber.d("update is null or 0 ");
                } else{
                    //long last = Long.parseLong(update);

                    if (now - update > 86400000){
                        toUpdate = true;
                        Timber.d("update is too old");
                    }
                    Timber.d("don't need to update");
                }

                String newsJsonStr = "";
                if (toUpdate) {
                    Timber.d("update news for: " + symbol);
                    URL newsUrl = NetworkUtils.getNewsUrl(symbol);
                    newsJsonStr = fetchData(newsUrl.toString());
                }

                if (histoJsonStr != null && !histoJsonStr.isEmpty() ){
                    Timber.d("historical result is success!");
                    result = GcmNetworkManager.RESULT_SUCCESS;

                    ContentValues value = new ContentValues();

                    value.put(CoinEntry.COLUMN_HISTO, histoJsonStr);

                    if (toUpdate && newsJsonStr.length()>0) {
                        value.put(CoinEntry.COLUMN_NEWS, newsJsonStr);

                        Timber.d("time now: " + now);
                        value.put(CoinEntry.COLUMN_UPDATE, now);
                    }

                    ContentResolver coinContentResolver = mContext.getContentResolver();

                    coinContentResolver.update(CoinEntry.CONTENT_URI,
                            value,
                            CoinEntry.COLUMN_SYMBOL + "=?",
                            new String[]{symbol}
                    );
                }else {
                    Timber.d("fetch historical data failed !");
                }
            }catch (Exception e){
                Timber.d(e.getMessage());
                e.getStackTrace();
            }
        }

        return result;
    }
}
