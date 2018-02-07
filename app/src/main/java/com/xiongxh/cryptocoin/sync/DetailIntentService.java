package com.xiongxh.cryptocoin.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;

import timber.log.Timber;


public class DetailIntentService extends IntentService {

    public DetailIntentService() {
        super("DetailIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Entering onHandleIntent() method ...");

        DetailTaskService detailTaskService = new DetailTaskService(this);

        Bundle args = new Bundle();

        if (intent.getStringExtra("tag").equals("detail")){
            Timber.d("tag is detail");
            args.putString("symbol", intent.getStringExtra("symbol"));
        }

        try {
            Timber.d("Inside try block ...");
            detailTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
