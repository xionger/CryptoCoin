package com.xiongxh.cryptocoin.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.xiongxh.cryptocoin.model.Coin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;

import timber.log.Timber;

public class CoinJsonUtils {

    public static String[] popCoins = {"BTC", "ETH", "XRP", "BCH", "ADA", "TRX", "EOS", "LTC",
            "XLM", "NEM", "NEO", "WTC", "VEN", "XMR", "ICX", "ETC", "DASH", "MIOTA", "BTG", "QTUM"};

    public static String loadCoins(Context context) {
        String json = null;
        AssetManager mngr = context.getAssets();

        try {
            InputStream inputStream = mngr.open("coinlist.json");

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }

        return json;
    }

    public static List<Coin> extractCoinsFromJson(String coinJsonStr, List<String> symbols){
        Timber.d("Entering extractCoinsFromJson() method...");
        if (TextUtils.isEmpty(coinJsonStr)){
            return null;
        }

        Timber.d("size of symbols: " + symbols.size());

        List<Coin> coins = new ArrayList<>();

        try {
            JSONObject basJsonResponse = new JSONObject(coinJsonStr);

            JSONObject dataObject = basJsonResponse.getJSONObject("Data");
            Timber.d("first 1000 chars of data object: " + dataObject.toString().substring(0, 1000));

            for (String symbol : symbols){
                Timber.d("Coin symbol: " + symbol);
                JSONObject properties = dataObject.getJSONObject(symbol);

                //
                // Timber.d("properties: " + properties.toString());

                if (properties != null){
                    //Coin coin = transferJSONObjectToCoin(properties, symbol);
                    //coins.add(coin);
                    try {
                        long coinId = Long.valueOf(properties.getString("Id"));
                        String coinName = properties.getString("CoinName");
                        String url = properties.getString("Url");
                        String imageUrl = properties.getString("ImageUrl");
                        String algorithm = properties.getString("Algorithm");
                        String proofType = properties.getString("ProofType");
                        long supply = Long.valueOf(properties.getString("TotalCoinSupply"));
                        int sponsor = 0;
                        boolean sponsored = properties.getBoolean("Sponsored");

                        if (sponsored) {
                            sponsor = 1;
                        }

                        Timber.d("Coin information: " + symbol + coinId + coinName);
                        Coin coin =
                                new Coin(symbol, coinId, coinName, url, imageUrl, algorithm, proofType, supply, sponsor);

                        coins.add(coin);

                    }catch (JSONException e){
                        e.getStackTrace();
                    }
                }
            }

        }catch (JSONException e){
            e.getStackTrace();
        }

        return coins;
    }

//    public static Coin extractCoinFromJson(String coinJsonStr, String symbol){
//        if (TextUtils.isEmpty(coinJsonStr)){
//            return null;
//        }
//
//        Coin coin = new Coin();
//        try {
//            JSONObject basJsonResponse = new JSONObject(coinJsonStr);
//
//            JSONObject dataObject = basJsonResponse.getJSONObject("data");
//            JSONObject properties = dataObject.getJSONObject(symbol);
//
//            if (properties != null){
//                coin = transferJSONObjectToCoin(properties, symbol);
//            }
//
//        }catch (JSONException e){
//            e.getStackTrace();
//        }
//
//        return coin;
//    }

    public static Coin transferJSONObjectToCoin(JSONObject properties, String symbol){
        Coin coin = new Coin();

        try {
            long coinId = Long.valueOf(properties.getString("id"));
            String coinName = properties.getString("CoinName");
            String url = properties.getString("Url");
            String imageUrl = properties.getString("ImageUrl");
            String algorithm = properties.getString("Algorithm");
            String proofType = properties.getString("ProofType");
            long supply = Long.valueOf(properties.getString("TotalCoinSupply"));
            int sponsor = 0;
            boolean sponsored = properties.getBoolean("Sponsored");

            if (sponsored) {
                sponsor = 1;
            }

            Timber.d("Coin information: " + symbol + coinId + coinName);
            coin = new Coin(symbol, coinId, coinName, url, imageUrl, algorithm, proofType, supply, sponsor);

        }catch (JSONException e){
            e.getStackTrace();
        }

        return coin;
    }

    public static ContentValues[] getCoinContentValueFromList(List<Coin> coins){
        Timber.d("Entering getCoinContentValueFromList() mehtod...");

        Timber.d("number of coins: " + coins.size());
        ContentValues[] contentValues = new ContentValues[coins.size()];

        int i = 0;
        for (Coin coin : coins){
            Timber.d("Iterate coins: " + coin.getCoinName());
            ContentValues value = getContentValueFromCoin(coin);
            contentValues[i] = value;
            i++;
        }

        Timber.d("number of content values: " + (i+1));

        return contentValues;
    }

    public static ContentValues getContentValueFromCoin(Coin coin){
        ContentValues value = new ContentValues();

        value.put(CoinEntry.COLUMN_SYMBOL, coin.getSymbol());
        value.put(CoinEntry.COLUMN_COIN_ID, coin.getCoinId());
        value.put(CoinEntry.COLUMN_NAME, coin.getCoinName());
        value.put(CoinEntry.COLUMN_COIN_URL, coin.getUrl());
        value.put(CoinEntry.COLUMN_IMAGE_URL, coin.getImageUrl());
        value.put(CoinEntry.COLUMN_ALGORITHM, coin.getAlgorithm());
        value.put(CoinEntry.COLUMN_PROOF_TYPE, coin.getProofType());
        value.put(CoinEntry.COLUMN_SUPPLY, coin.getSupply());
        value.put(CoinEntry.COLUMN_SPONSORED, coin.getSponsor());

        return value;
    }
}