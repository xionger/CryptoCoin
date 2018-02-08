package com.xiongxh.cryptocoin.utilities;

import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;
public class ConstantsUtils {

    public static final String[] COIN_COLUMNS = {
            CoinEntry.TABLE_NAME + "." + CoinEntry._ID,
            CoinEntry.COLUMN_SYMBOL,
            CoinEntry.COLUMN_NAME,
            CoinEntry.COLUMN_COIN_URL,
            CoinEntry.COLUMN_IMAGE_URL,
            CoinEntry.COLUMN_ALGORITHM,
            CoinEntry.COLUMN_PROOF_TYPE,
            CoinEntry.COLUMN_TOTAL_SUPPLY,
            CoinEntry.COLUMN_SPONSOR,
            CoinEntry.COLUMN_SUPPLY,
            CoinEntry.COLUMN_PRICE,
            CoinEntry.COLUMN_MKTCAP,
            CoinEntry.COLUMN_VOL24H,
            CoinEntry.COLUMN_VOL24H2,
            CoinEntry.COLUMN_OPEN24H,
            CoinEntry.COLUMN_HIGH24H,
            CoinEntry.COLUMN_LOW24H,
            CoinEntry.COLUMN_TREND,
            CoinEntry.COLUMN_CHANGE,
            CoinEntry.COLUMN_HISTO,
            CoinEntry.COLUMN_NEWS
    };

    public static final int POSITION_ID = 0;
    public static final int POSITION_SYMBOL = 1;
    public static final int POSITION_NAME = 2;
    public static final int POSITION_COIN_URL = 3;
    public static final int POSITION_IMAGE_URL = 4;
    public static final int POSITION_ALGORITHM = 5;
    public static final int POSITION_PROOF_TYPE = 6;
    public static final int POSITION_TOTAL_SUPPLY = 7;
    public static final int POSITION_SPONSOR = 8;
    public static final int POSITION_SUPPLY = 9;
    public static final int POSITION_PRICE = 10;
    public static final int POSITION_MKTCAP = 11;
    public static final int POSITION_VOL24H = 12;
    public static final int POSITION_VOL24H2 = 13;
    public static final int POSITION_OPEN24H = 14;
    public static final int POSITION_HIGH24H = 15;
    public static final int POSITION_LOW24H = 16;
    public static final int POSITION_TREND = 17;
    public static final int POSITION_CHANGE = 18;
    public static final int POSITION_HISTO = 19;
    public static final int POSITION_NEWS = 20;
}
