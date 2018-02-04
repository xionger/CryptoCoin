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
                + CoinEntry.COLUMN_COIN_ID + " REAL NOT NULL, "
                + CoinEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_COIN_URL + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_ALGORITHM + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_PROOF_TYPE + " TEXT NOT NULL, "
                + CoinEntry.COLUMN_SUPPLY + " REAL NOT NULL, "
                + CoinEntry.COLUMN_SPONSORED + " INTEGER NOT NULL, "
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