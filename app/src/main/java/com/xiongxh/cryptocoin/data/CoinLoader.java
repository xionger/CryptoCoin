package com.xiongxh.cryptocoin.data;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

import com.xiongxh.cryptocoin.utilities.ConstantsUtils;
import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;

public class CoinLoader extends CursorLoader {

    public static CoinLoader newAllCoinsInstance(Context context){
        return new CoinLoader(context, CoinEntry.CONTENT_URI);
    }

    public static CoinLoader newInstanceForCoinSymbol(Context context, String symbol){
        return new CoinLoader(context, CoinEntry.builUriWithSympol(symbol));
    }

//    public static CoinLoader newInstanceForCoinIdx(Context context, long idx){
//        return new CoinLoader(context, CoinEntry.buildCoinItemUri(idx));
//    }

    public CoinLoader(Context context, Uri uri) {
        super(context,
                uri,
                ConstantsUtils.COIN_COLUMNS,
                null,
                null,
                CoinEntry.DEFAULT_SORT);
    }
}
