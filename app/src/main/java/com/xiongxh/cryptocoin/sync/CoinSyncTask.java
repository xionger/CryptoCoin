package com.xiongxh.cryptocoin.sync;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;
import com.xiongxh.cryptocoin.data.CoinDbContract;
import com.xiongxh.cryptocoin.model.Coin;
import com.xiongxh.cryptocoin.utilities.CoinJsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;

public class CoinSyncTask {
//    private final String[] popCoinSymbols = {"BTC", "ETH", "XRP", "BCH", "ADA", "TRX", "EOS", "LTC"};
//
//    private Context mContext;
//    private boolean isUpdate;
//
//    private List<String> coinSymbols = new ArrayList<>();
//
//    public CoinSyncTask() {
//    }
//
//    public CoinSyncTask(Context context) {
//        mContext = context;
//    }
//
//    public boolean onRunTask(Context context, TaskParams taskParams) {
//        Cursor initQueryCursor;
//
//        if (mContext == null){
//            mContext = context;
//        }
//
//        if (taskParams.getTag().equals("init") || taskParams.getTag().equals("periodic")){
//            isUpdate = true;
//
//            initQueryCursor = mContext.getContentResolver()
//                    .query(CoinEntry.CONTENT_URI, ConstantsUtils.COIN_COLUMNS, null, null, null);
//
//            if (initQueryCursor == null || initQueryCursor.getCount() == 0){
//                coinSymbols.clear();
//                coinSymbols.addAll(Arrays.asList(popCoinSymbols));
//            }
//        }else if (taskParams.getTag().equals("add")){
//            isUpdate = false;
//
//            String toAddCoin = taskParams.getExtras().getString("symbol");
//
//            coinSymbols.add(toAddCoin);
//            Set<String> hs = new HashSet<>();
//            hs.addAll(coinSymbols);
//            coinSymbols.clear();
//            coinSymbols.addAll(hs);
//        }
//
//        boolean result = false;
//
//        try {
//            String coinsJasonStr = CoinJsonUtils.loadCoins(mContext);
//
//            if (coinsJasonStr != null && !coinsJasonStr.isEmpty()){
//                result = true;
//            }
//            List<Coin> coins = CoinJsonUtils.extractCoinsFromJson(coinsJasonStr, coinSymbols);
//            ContentValues[] coinValues = CoinJsonUtils.getCoinContentValueFromList(coins);
//
//            Uri dirUri = CoinEntry.buildCoinDirUri();
//
//            ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();
//
//            if (coinValues != null && coinValues.length != 0){
//                for (ContentValues value : coinValues){
//                    cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(value).build());
//                }
//                ContentResolver coinContentResolver = mContext.getContentResolver();
//                coinContentResolver.applyBatch(CoinDbContract.CONTENT_AUTHORITY, cpo);
//            }
//        }catch (Exception e){
//            e.getStackTrace();
//        }
//
//        return result;
//    }

}