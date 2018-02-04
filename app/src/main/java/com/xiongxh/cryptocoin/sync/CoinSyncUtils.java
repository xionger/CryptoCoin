package com.xiongxh.cryptocoin.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class CoinSyncUtils {

    public static void startImmediateSync(@NonNull final Context context){
        Intent intentToSyncImmediately = new Intent(context, CoinSyncIntentService.class);
        context.startActivity(intentToSyncImmediately);
    }
}
