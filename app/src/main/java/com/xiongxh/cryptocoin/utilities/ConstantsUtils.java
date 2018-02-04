package com.xiongxh.cryptocoin.utilities;

import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;
public class ConstantsUtils {

    public static final String[] COIN_COLUMNS = {
            CoinEntry.TABLE_NAME + "." + CoinEntry._ID,
            CoinEntry.COLUMN_SYMBOL,
            CoinEntry.COLUMN_COIN_ID,
            CoinEntry.COLUMN_NAME,
            CoinEntry.COLUMN_COIN_URL,
            CoinEntry.COLUMN_IMAGE_URL,
            CoinEntry.COLUMN_ALGORITHM,
            CoinEntry.COLUMN_PROOF_TYPE,
            CoinEntry.COLUMN_SUPPLY,
            CoinEntry.COLUMN_SPONSORED
    };


    public static final int POSITION_ID = 0;
    public static final int POSITION_SYMBOL = 1;
    public static final int POSITION_COIN_ID = 2;
    public static final int POSITION_NAME = 3;
    public static final int POSITION_COIN_URL = 4;
    public static final int POSITION_IMAGE_URL = 5;
    public static final int POSITION_ALGORITHM = 6;
    public static final int POSITION_PROOF_TYPE = 7;
    public static final int POSITION_SUPPLY = 8;
    public static final int POSITION_SPONSORED = 9;
}
