package com.xiongxh.cryptocoin.utilities;

import android.content.Context;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.xiongxh.cryptocoin.data.CoinPreferences;

import java.util.Calendar;

public class DateFormatter implements IAxisValueFormatter {

    private Context mContext;
        public DateFormatter(Context context) {
            super();
            mContext = context;
        }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String intervalPref = CoinPreferences.getPreferredInterval(mContext);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) value*1000);

        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
        int mAP = calendar.get(Calendar.AM_PM);

        String minStr = mMinute < 10 ? "0" + mMinute : "" + mMinute;
        String apStr = mAP == 0 ? "AM" : "PM";

        String histoTime = mMonth + "/" + mDay + " " + mHour%12 + apStr;

        if (intervalPref.equals("day")){
            histoTime = mMonth + "/" + mDay;
        } else if (intervalPref.equals("minute")){
            histoTime = mHour%12 + ":" + minStr + apStr;
        }

        return histoTime;
    }

}
