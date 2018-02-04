package com.xiongxh.cryptocoin.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;

import timber.log.Timber;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CoinSyncIntentService extends IntentService {

    public CoinSyncIntentService(){
        super("CoinSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //CoinSyncTask.syncCoins(this);
        //CoinSyncTask coinSyncTask = new CoinSyncTask(this);

        Timber.d("Entering onHandleIntent() method ...");
        CoinTaskService coinTaskService = new CoinTaskService(this);

        Bundle args = new Bundle();

        if (intent.getStringExtra("tag").equals("add")){
            Timber.d("tag is add");
            args.putString("symbol", intent.getStringExtra("symbol"));
        }

        try {
            Timber.d("Inside try block ...");
            coinTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
        }catch (Exception e){
            e.getStackTrace();
        }
    }

//    // TODO: Rename actions, choose action names that describe tasks that this
//    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//    private static final String ACTION_FOO = "com.xiongxh.cryptocoin.sync.action.FOO";
//    private static final String ACTION_BAZ = "com.xiongxh.cryptocoin.sync.action.BAZ";
//
//    // TODO: Rename parameters
//    private static final String EXTRA_PARAM1 = "com.xiongxh.cryptocoin.sync.extra.PARAM1";
//    private static final String EXTRA_PARAM2 = "com.xiongxh.cryptocoin.sync.extra.PARAM2";
//
//    public CoinSyncIntentService() {
//        super("CoinSyncIntentService");
//    }
//
//    /**
//     * Starts this service to perform action Foo with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, CoinSyncIntentService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }
//
//    /**
//     * Starts this service to perform action Baz with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, CoinSyncIntentService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
//        }
//    }
//
//    /**
//     * Handle action Foo in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionFoo(String param1, String param2) {
//        // TODO: Handle action Foo
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    /**
//     * Handle action Baz in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionBaz(String param1, String param2) {
//        // TODO: Handle action Baz
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
