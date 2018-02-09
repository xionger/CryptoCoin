package com.xiongxh.cryptocoin.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class CoinDbContract {
    public static final String CONTENT_AUTHORITY = "com.xiongxh.cryptocoin";
    static final String PATH_COINS = "coins";
    static final String PATH_COIN_SYMBOL = "coins/*";
    private static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private CoinDbContract() {
    }

    public static final class CoinEntry implements BaseColumns {

        public static final String TABLE_NAME = "coins";

//        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.xiongxh.cryptocoin.coins";
//        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.xiongxh.cryptocoin.coins";

        public  static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COINS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/" + CONTENT_AUTHORITY + "/" + PATH_COINS;

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_COINS).build();

        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COIN_URL = "coin_url";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_ALGORITHM = "algorithm";
        public static final String COLUMN_PROOF_TYPE = "proof_type";
        public static final String COLUMN_TOTAL_SUPPLY = "total_supply";
        public static final String COLUMN_SPONSOR = "sponsor";
        public static final String COLUMN_SUPPLY = "supply";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_MKTCAP = "mktcap";
        public static final String COLUMN_VOL24H = "vol24h";
        public static final String COLUMN_VOL24H2 = "vol24h2";
        public static final String COLUMN_OPEN24H = "open24h";
        public static final String COLUMN_HIGH24H = "high24h";
        public static final String COLUMN_LOW24H = "low24h";
        public static final String COLUMN_TREND = "trend";
        public static final String COLUMN_CHANGE = "change";
        public static final String COLUMN_HISTO = "histo";
        public static final String COLUMN_NEWS = "news";
        public static final String COLUMN_UPDATE = "lasttime";

        public static final String DEFAULT_SORT = COLUMN_SYMBOL + " ASC";

        /** Matches: /coins/ */
        public static Uri buildCoinDirUri() {
            return BASE_URI.buildUpon().appendPath("coins").build();
        }

        /** Matches: /coins/[_id]/ */
        public static Uri buildCoinItemUri(long _id) {
            return BASE_URI.buildUpon().appendPath("coins").appendPath(Long.toString(_id)).build();
        }

        /** Read coin IDX coin detail URI. */
        public static long getCoinItemIdx(Uri coinItemUri) {
            return Long.parseLong(coinItemUri.getPathSegments().get(1));
        }

        public static Uri builUriWithSympol(String symbol){
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }

        public String getSymbolFromUri(Uri uri){
            return uri.getLastPathSegment();
        }

    }
}