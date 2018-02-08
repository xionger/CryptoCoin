package com.xiongxh.cryptocoin.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Request;
import timber.log.Timber;

public class NetworkUtils {

    private static final String BASE_URL = "https://min-api.cryptocompare.com/data/";
    private static final String PARAM_FROM_SYMBOL = "fsym";
    private static final String PARAM_TO_SYMBOL = "tsym";
    private static final String PARAM_FROM_SYMBOLS = "fsyms";
    private static final String PARAM_TO_SYMBOLS = "tsyms";
    private static final String PARAM_LIMIT = "limit";
    private static final String pref_price_unit = "USD";
    private static final String pref_interval = "histohour";

    public static boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null)
                if(networkInfo.isConnected())
                    return true;
        }
        return false;
    }

    public static URL getPriceUrl(String symbols){
        Uri priceUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath("pricemultifull")
                .appendQueryParameter(PARAM_FROM_SYMBOLS, symbols)
                .appendQueryParameter(PARAM_TO_SYMBOLS, pref_price_unit)
                .build();
        try {
            URL priceUrl = new URL(priceUri.toString());
            Timber.d("Calling url: " + priceUrl);
            return priceUrl;

        }catch (MalformedURLException e){
            Timber.d(e.getMessage());
            return null;
        }
    }

    public static URL getHistoUrl(String fromSymbol){
        String limit = "30";
        if (pref_interval.equals("histominute")){
            limit = "900";
        }

        Uri histUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(pref_interval)
                .appendQueryParameter(PARAM_FROM_SYMBOL, fromSymbol)
                .appendQueryParameter(PARAM_TO_SYMBOL, pref_price_unit)
                .appendQueryParameter(PARAM_LIMIT, limit)
                .build();
        try {
            URL histUrl = new URL(histUri.toString());
            Timber.d("Calling url: " + histUrl);
            return histUrl;

        }catch (MalformedURLException e){
            Timber.d(e.getMessage());
            return null;
        }
    }

    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/everything?";
    private static final String PARAM_QUERY = "q";
    private static final String PARAM_PAGE_SIZE = "pageSize";
    private static final String PARAM_SORT_BY = "sortBy";
    private static final String PARAM_API_KEY ="apiKey";

    private static final String PUBLISHED = "publishedAt";
    private static final String KEY = "06326f0eb57d4f21a0486f34d2179e84";
    //https://newsapi.org/v2/everything?q=BTC+crypto&sortBy=publishedAt&pageSize=50&apiKey=06326f0eb57d4f21a0486f34d2179e84
    public static URL getNewsUrl(String symbol){

        Uri newsUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, symbol + "+crypto")
                .appendQueryParameter(PARAM_PAGE_SIZE, "50")
                .appendQueryParameter(PARAM_SORT_BY, PUBLISHED)
                .appendQueryParameter(PARAM_API_KEY, KEY)
                .build();
        try {
            URL newsUrl = new URL(newsUri.toString());
            Timber.d("Calling url: " + newsUrl);
            return newsUrl;

        }catch (MalformedURLException e){
            Timber.d(e.getMessage());
            return null;
        }
    }

    public static URL getUrl(Context context){
        //String sortStr = MoviePreferences.getPreferredSortType(context);
        //mSortType = sortStr;

        //return buildUrlWithQuery(sortStr);
        return null;
    }

    private static URL buildUrlWithQuery(String sortType){
        /**
         final String SORT_BY_PARAM = "sort_by";
         final String RELEASE_DATE_PARAM = "primary_release_date.gte";
         final String release_after = "2015-01-01";
         final String VOTE_COUNT_PARAM = "vote_count.gte";
         final String vote_count_threshold = "100";
         Uri buildUri = Uri.parse(BASE_URL).buildUpon()
         .appendQueryParameter(SORT_BY_PARAM, sortType)
         .appendQueryParameter(RELEASE_DATE_PARAM, release_after)
         .appendQueryParameter(VOTE_COUNT_PARAM, vote_count_threshold)
         .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
         .build();
         */
//        if (sortType.equals("top_rated")){
//            BASE_URL = BASE_URL_TOP_RATED;
//        } else {
//            BASE_URL = BASE_URL_POPULAR;
//        }
//
//        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
//                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
//                .build();
//
//        try {
//            URL url = new URL(buildUri.toString());
//            Timber.d( "Calling url: " + url);
//            return url;
//        }catch (MalformedURLException e){
//            Timber.d(e.getMessage());
//            return null;
//        }
        return null;
    }

}
