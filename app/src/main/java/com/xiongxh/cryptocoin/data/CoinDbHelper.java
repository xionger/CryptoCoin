package com.xiongxh.cryptocoin.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;

public class CoinDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cryptocoins.db";
    private static final int VERSION = 2;

    CoinDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String builder = "CREATE TABLE " + CoinEntry.TABLE_NAME + " ("
                + CoinEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CoinEntry.COLUMN_SYMBOL + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_COIN_URL + " TEXT, "
                + CoinEntry.COLUMN_IMAGE_URL + " TEXT, "
                + CoinEntry.COLUMN_ALGORITHM + " TEXT, "
                + CoinEntry.COLUMN_PROOF_TYPE + " TEXT, "
                + CoinEntry.COLUMN_TOTAL_SUPPLY + " REAL, "
                + CoinEntry.COLUMN_SPONSOR + " INTEGER NOT NULL, "
                + CoinEntry.COLUMN_SUPPLY + " REAL, "
                + CoinEntry.COLUMN_PRICE + " REAL, "
                + CoinEntry.COLUMN_MKTCAP + " REAL, "
                + CoinEntry.COLUMN_VOL24H + " REAL, "
                + CoinEntry.COLUMN_VOL24H2 + " REAL, "
                + CoinEntry.COLUMN_OPEN24H + " REAL, "
                + CoinEntry.COLUMN_HIGH24H + " REAL, "
                + CoinEntry.COLUMN_LOW24H + " REAL, "
                + CoinEntry.COLUMN_TREND + " REAL, "
                + CoinEntry.COLUMN_CHANGE + " REAL, "
                + CoinEntry.COLUMN_HISTO + " TEXT, "
                //+ CoinEntry.COLUMN_NEWS + " TEXT, "
                + "UNIQUE (" + CoinEntry.COLUMN_SYMBOL + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + CoinEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}