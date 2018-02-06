package com.xiongxh.cryptocoin.utilities;

import android.app.job.JobInfo;
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

    public static final String[] PRICE_UNITS ={"USD", "BTC"};
    public static String pref_price_unit = "USD";

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

    public static boolean isSymbolValid(Context context, String symbol){
        boolean isValid = false;

        try {
            String coinsStr = loadCoins(context);
            JSONObject coinJsonResponse = new JSONObject(coinsStr);
            JSONObject dataObject = coinJsonResponse.getJSONObject("Data");

            JSONObject symbolObject = dataObject.getJSONObject(symbol);

            if (symbolObject != null ){
                isValid = true;
            }

        }catch (JSONException e){
            e.getStackTrace();
        }

        return isValid;
    }

    public static List<Coin> extractCoinsFromJson(String coinJsonStr, String priceJsonStr, List<String> symbols){
        Timber.d("Entering extractCoinsFromJson() method...");
        if (TextUtils.isEmpty(coinJsonStr) || TextUtils.isEmpty(priceJsonStr)){
            return null;
        }

        Timber.d("size of symbols: " + symbols.size());

        List<Coin> coins = new ArrayList<>();

        try {
            JSONObject coinJsonResponse = new JSONObject(coinJsonStr);
            JSONObject dataObject = coinJsonResponse.getJSONObject("Data");

            //Timber.d("first 500 chars of data object: " + dataObject.toString().substring(0, 500));

            JSONObject priceJsonResponse = new JSONObject(priceJsonStr);
            JSONObject rawPriceObject = priceJsonResponse.getJSONObject("RAW");

            //Timber.d("price object: " + rawPriceObject.toString());

            for (String symbol : symbols){
                Timber.d("Coin symbol: " + symbol);

                Coin coin = new Coin(symbol);

                JSONObject coinProperties = dataObject.getJSONObject(symbol);

                if (coinProperties != null){
                    Timber.d(symbol + "'s coinProperties: " + coinProperties.toString());
                    String coinName = coinProperties.getString("CoinName");
                    String url = coinProperties.getString("Url");
                    String imageUrl = coinProperties.getString("ImageUrl");
                    String algorithm = coinProperties.getString("Algorithm");
                    String proofType = coinProperties.getString("ProofType");
                    String tsStr = coinProperties.getString("TotalCoinSupply");
                    int sponsor = 0;
                    boolean sponsored = coinProperties.getBoolean("Sponsored");

                    if (sponsored) {
                        sponsor = 1;
                    }

                    coin.setCoinName(coinName);
                    coin.setUrl(url);
                    coin.setImageUrl(imageUrl);
                    coin.setAlgorithm(algorithm);
                    coin.setProofType(proofType);
                    coin.setSponsor(sponsor);

                    long totalSupply = 0;
                    if (tsStr.length() > 5){
                        totalSupply = Long.valueOf(tsStr);
                    }

                    coin.setTotalSupply(totalSupply);
                }

                JSONObject coinPriceObject = rawPriceObject.getJSONObject(symbol);
                Timber.d(symbol + "'s RAW price data: " + coinPriceObject.toString());
                JSONObject priceProperties = coinPriceObject.getJSONObject(pref_price_unit);
                Timber.d(symbol + "'s price data: " + priceProperties.toString());

                if (priceProperties != null){
                    double price = priceProperties.getDouble("PRICE");
                    double mktcap = priceProperties.getDouble("MKTCAP");
                    double vol24h = priceProperties.getDouble("VOLUME24HOUR");
                    double vol24h2 = priceProperties.getDouble("VOLUME24HOURTO");
                    double open24h = priceProperties.getDouble("OPEN24HOUR");
                    double high24h = priceProperties.getDouble("HIGH24HOUR");
                    double low24h = priceProperties.getDouble("LOW24HOUR");
                    double trend = priceProperties.getDouble("CHANGEPCT24HOUR");
                    double change = priceProperties.getDouble("CHANGE24HOUR");
                    double supply = priceProperties.getDouble("SUPPLY");

                    coin.setPrice(price);
                    coin.setMktcap(mktcap);
                    coin.setVol24h(vol24h);
                    coin.setVol24h2(vol24h2);
                    coin.setOpen24h(open24h);
                    coin.setHigh24h(high24h);
                    coin.setLow24h(low24h);
                    coin.setTrend(trend);
                    coin.setChange(change);
                    coin.setSupply(supply);

                    Timber.d(coin.getSymbol() + "Price: " + coin.getPrice());
                }

//                coin.setNews("Fake news. To be added");
//                coin.setHisto("Fake histo data. To be added");

                coins.add(coin);
            }

        }catch (JSONException e){
            e.getStackTrace();
        }

        return coins;
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
        value.put(CoinEntry.COLUMN_NAME, coin.getCoinName());
        value.put(CoinEntry.COLUMN_COIN_URL, coin.getUrl());
        value.put(CoinEntry.COLUMN_IMAGE_URL, coin.getImageUrl());
        value.put(CoinEntry.COLUMN_ALGORITHM, coin.getAlgorithm());
        value.put(CoinEntry.COLUMN_PROOF_TYPE, coin.getProofType());
        value.put(CoinEntry.COLUMN_TOTAL_SUPPLY, coin.getTotalSupply());
        value.put(CoinEntry.COLUMN_SPONSOR, coin.getSponsor());
        value.put(CoinEntry.COLUMN_SUPPLY, coin.getSupply());
        value.put(CoinEntry.COLUMN_PRICE, coin.getPrice());
        value.put(CoinEntry.COLUMN_MKTCAP, coin.getMktcap());
        value.put(CoinEntry.COLUMN_VOL24H, coin.getVol24h());
        value.put(CoinEntry.COLUMN_VOL24H2, coin.getVol24h2());
        value.put(CoinEntry.COLUMN_OPEN24H, coin.getOpen24h());
        value.put(CoinEntry.COLUMN_HIGH24H, coin.getHigh24h());
        value.put(CoinEntry.COLUMN_LOW24H, coin.getLow24h());
        value.put(CoinEntry.COLUMN_TREND, coin.getTrend());
        value.put(CoinEntry.COLUMN_CHANGE, coin.getChange());
        //value.put(CoinEntry.COLUMN_NEWS, coin.getNews());

        return value;
    }

    //    public static Coin extractCoinFromJson(String symbol, String coinsJsonStr, String priceJsonStr, String histoJasonStr){
//
//        Coin coin = new Coin(symbol);
//
//        try {
//            JSONObject coinsJsonResponse = new JSONObject(coinsJsonStr);
//            JSONObject dataObject = coinsJsonResponse.getJSONObject("Data");
//            JSONObject coinProperties = dataObject.getJSONObject(symbol);
//
//            if (coinProperties != null){
//                String coinName = coinProperties.getString("CoinName");
//                String url = coinProperties.getString("Url");
//                String imageUrl = coinProperties.getString("ImageUrl");
//                String algorithm = coinProperties.getString("Algorithm");
//                String proofType = coinProperties.getString("ProofType");
//                long totalSupply = Long.valueOf(coinProperties.getString("TotalCoinSupply"));
//                int sponsor = 0;
//                boolean sponsored = coinProperties.getBoolean("Sponsored");
//
//                if (sponsored) {
//                    sponsor = 1;
//                }
//
//                coin.setCoinName(coinName);
//                coin.setUrl(url);
//                coin.setImageUrl(imageUrl);
//                coin.setAlgorithm(algorithm);
//                coin.setProofType(proofType);
//                coin.setTotalSupply(totalSupply);
//                coin.setSponsor(sponsor);
//            }
//
//            JSONObject priceJsonResponse = new JSONObject(priceJsonStr);
//            JSONObject rawPriceObject = priceJsonResponse.getJSONObject("RAW");
//            JSONObject coinPriceObject = rawPriceObject.getJSONObject(symbol);
//            JSONObject priceProperties = coinPriceObject.getJSONObject(pref_price_unit);
//
//            if (priceProperties != null){
//                double price = priceProperties.getDouble("PRICE");
//                double mktcap = priceProperties.getDouble("MKTCAP");
//                double vol24h = priceProperties.getDouble("VOLUME24HOUR");
//                double vol24h2 = priceProperties.getDouble("VOLUME24HOURTO");
//                double open24h = priceProperties.getDouble("OPEN24HOUR");
//                double high24h = priceProperties.getDouble("HIGH24HOUR");
//                double low24h = priceProperties.getDouble("LOW24HOUR");
//                double trend = priceProperties.getDouble("CHANGEPCT24HOUR");
//                double supply = priceProperties.getDouble("SUPPLY");
//
//                coin.setPrice(price);
//                coin.setMktcap(mktcap);
//                coin.setVol24h(vol24h);
//                coin.setVol24h2(vol24h2);
//                coin.setOpen24h(open24h);
//                coin.setHigh24h(high24h);
//                coin.setLow24h(low24h);
//                coin.setTrend(trend);
//                coin.setSupply(supply);
//
//                Timber.d(coin.getSymbol() + "Price: " + coin.getPrice());
//            }
//
//            JSONObject histoJsonResponse = new JSONObject(histoJasonStr);
//            JSONObject histoDataObject = histoJsonResponse.getJSONObject("Data");
//
//            if (histoDataObject != null){
//                coin.setHisto(histoDataObject.toString());
//            }
//
//            coin.setNews("Fake news. To be added");
//
//        }catch (JSONException e){
//            e.getStackTrace();
//        }
//
//        return coin;
//    }
//

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

//    public static Coin transferJSONObjectToCoin(JSONObject properties, String symbol){
//        Coin coin = new Coin();
//
//        try {
//            long coinId = Long.valueOf(properties.getString("id"));
//            String coinName = properties.getString("CoinName");
//            String url = properties.getString("Url");
//            String imageUrl = properties.getString("ImageUrl");
//            String algorithm = properties.getString("Algorithm");
//            String proofType = properties.getString("ProofType");
//            long supply = Long.valueOf(properties.getString("TotalCoinSupply"));
//            int sponsor = 0;
//            boolean sponsored = properties.getBoolean("Sponsored");
//
//            if (sponsored) {
//                sponsor = 1;
//            }
//
//            Timber.d("Coin information: " + symbol + coinId + coinName);
//            coin = new Coin(symbol, coinId, coinName, url, imageUrl, algorithm, proofType, supply, sponsor);
//
//        }catch (JSONException e){
//            e.getStackTrace();
//        }
//
//        return coin;
//    }


}