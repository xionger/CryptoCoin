package com.xiongxh.cryptocoin.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;
import com.xiongxh.cryptocoin.R;

import timber.log.Timber;

public class CoinSyncIntentService extends IntentService {

    public CoinSyncIntentService(){
        super("CoinSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Timber.d("Entering onHandleIntent() method ...");
        CoinTaskService coinTaskService = new CoinTaskService(this);

        Bundle args = new Bundle();

        if (intent.getStringExtra(getString(R.string.tag_tag)).equals(getString(R.string.tag_add_value))){
            //Timber.d("tag is add");
            args.putString(getString(R.string.symbol_tag), intent.getStringExtra(getString(R.string.symbol_tag)));
        }

        try {
            //Timber.d("Inside try block ...");
            coinTaskService.onRunTask(new TaskParams(intent.getStringExtra(getString(R.string.tag_tag)), args));
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
