package com.xiongxh.cryptocoin.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.xiongxh.cryptocoin.R;

public class CoinPreferences {
    public static String getPreferredUnit(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForUnit = context.getString(R.string.pref_unit_key);
        String defaultUnit = context.getString(R.string.pref_unit_usd_value);

        return sp.getString(keyForUnit, defaultUnit);
    }

    public static String getPreferredInterval(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForInterval = context.getString(R.string.pref_interval_key);
        String defaultInterval = context.getString(R.string.pref_interval_histohour_value);

        return sp.getString(keyForInterval, defaultInterval);
    }
}
