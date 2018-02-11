package com.xiongxh.cryptocoin.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.coindetails.CoinDetailActivity;
import com.xiongxh.cryptocoin.coins.CoinsActivity;
import com.xiongxh.cryptocoin.sync.CoinTaskService;

/**
 * Implementation of App Widget functionality.
 */
public class CoinWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (CoinTaskService.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int ids[] = manager.getAppWidgetIds(new ComponentName(context, getClass()));
            manager.notifyAppWidgetViewDataChanged(ids, R.id.widget_coin_list);
        }
    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_coin_provider);
        Intent intent = new Intent(context, CoinsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_header, pendingIntent);

        setRemoteAdapter(context, views, appWidgetId);

        //views.setTextViewText(R.id.appwidget_text, R.id.empty_text);


        Intent clickIntentTemplate = new Intent(context, CoinDetailActivity.class);
        PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntentTemplate)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_coin_list, clickPendingIntentTemplate);

        // Instruct the widget manager to update the widget
        views.setEmptyView(R.id.widget_coin_list, R.id.empty_text);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views, int appWidgetId) {
        Intent intent = new Intent(context, CoinWidgetRemoteService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.widget_coin_list, intent);
    }
}

