package com.xiongxh.cryptocoin.model;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.xiongxh.cryptocoin.utilities.ConstantsUtils;

public class BasicCoin implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("Symbol")
    private String symbol;
    @SerializedName("price")
    private String price;
    @SerializedName("trend")
    private String trend;

    protected BasicCoin(Parcel in) {
        id = in.readInt();
        symbol = in.readString();
        price = in.readString();
        trend = in.readString();
    }

    public BasicCoin(){}

    public static final Creator<BasicCoin> CREATOR = new Creator<BasicCoin>() {
        @Override
        public BasicCoin createFromParcel(Parcel in) {
            return new BasicCoin(in);
        }

        @Override
        public BasicCoin[] newArray(int size) {
            return new BasicCoin[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice(){
        return this.price == null ? "0.00" : this.price;
    }

    public void setTrend(String trend){
        this.trend = trend;
    }

    public String getTrend(){
        return this.trend == null ? "0.00" : this.trend;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(symbol);
        parcel.writeString(price);
        parcel.writeString(trend);
    }

    public static BasicCoin basicCoinFromCursor(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            BasicCoin basicCoin = new BasicCoin();
            basicCoin.setPrice(cursor.getString(ConstantsUtils.POSITION_PRICE));
            basicCoin.setTrend(cursor.getString(ConstantsUtils.POSITION_TREND));
            basicCoin.setSymbol(cursor.getString(ConstantsUtils.POSITION_SYMBOL));
            return basicCoin;
        }
        return null;
    }
}
