package com.xiongxh.cryptocoin.widget;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.xiongxh.cryptocoin.R;
import com.xiongxh.cryptocoin.coindetails.CoinDetailActivity;
import com.xiongxh.cryptocoin.model.BasicCoin;
import com.xiongxh.cryptocoin.model.Coin;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;
import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;

public class CoinWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
    private Cursor cursor = null;
    private final Context mContext;
    private final int mWidgetId;

    public CoinWidgetRemoteViewFactory(Context context, Intent intent){
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        //cursor = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, STOCK_COLUMNS, QuoteColumns.ISCURRENT + " = ?", new String[]{"1"}, null);

        cursor = mContext.getContentResolver()
                .query(CoinEntry.CONTENT_URI, ConstantsUtils.COIN_COLUMNS, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_coin_list);

        BasicCoin basicCoin = BasicCoin.basicCoinFromCursor(cursor);

//        String symbol = cursor.getString(ConstantsUtils.POSITION_SYMBOL);
//        double price = cursor.getDouble(ConstantsUtils.POSITION_PRICE);
        double trend  = cursor.getDouble(ConstantsUtils.POSITION_TREND);

//        Coin coin = new Coin();
//        coin.setSymbol(cursor.getString(ConstantsUtils.POSITION_SYMBOL));
//        coin.setPrice(cursor.getDouble(ConstantsUtils.POSITION_PRICE));
//        coin.setTrend(trend);

        views.setTextViewText(R.id.wd_symbol, basicCoin.getSymbol());
        views.setTextViewText(R.id.wd_price, basicCoin.getPrice());
        views.setTextViewText(R.id.wd_trend, basicCoin.getTrend());
        if (trend > 0) {
            views.setInt(R.id.wd_trend, mContext.getString(R.string.setBackgroundResource), R.drawable.price_increase_green);
        } else {
            views.setInt(R.id.wd_trend, mContext.getString(R.string.setBackgroundResource), R.drawable.price_decrease_red);
        }
        //views.setTextViewText(R.id.change, data.getString(INDEX_PERCENT_CHANGE));

//        Intent intent = new Intent(mContext, CoinDetailActivity.class);
//        intent.putExtra(mContext.getString(R.string.symbol_tag), symbol);
//        views.setOnClickFillInIntent(R.id.wd_coin_info, intent);

        final Intent fillInIntent = new Intent();
        fillInIntent.putExtra("basiccoin", basicCoin);
        views.setOnClickFillInIntent(R.id.wd_coin_basic, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.widget_item_coin_list);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position))
            return cursor.getLong(ConstantsUtils.POSITION_ID);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
