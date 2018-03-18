package com.xiongxh.cryptocoin.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;
import com.xiongxh.cryptocoin.R;

import timber.log.Timber;


public class DetailIntentService extends IntentService {

    public DetailIntentService() {
        super("DetailIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Timber.d("Entering onHandleIntent() method ...");

        DetailTaskService detailTaskService = new DetailTaskService(this);

        Bundle args = new Bundle();

        if (intent.getStringExtra(getString(R.string.tag_tag)).equals(getString(R.string.tag_detail_value))){
            //Timber.d("tag is detail");
            args.putString(getString(R.string.symbol_tag), intent.getStringExtra(getString(R.string.symbol_tag)));
        }

        try {
            //Timber.d("Inside try block ...");
            detailTaskService.onRunTask(new TaskParams(intent.getStringExtra(getString(R.string.tag_tag)), args));
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
