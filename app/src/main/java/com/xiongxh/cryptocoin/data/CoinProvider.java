package com.xiongxh.cryptocoin.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.xiongxh.cryptocoin.data.CoinDbContract.CoinEntry;

import timber.log.Timber;

public class CoinProvider extends ContentProvider {
    private static final int COINS = 100;
    private static final int COIN_SYMBOL = 101;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    private CoinDbHelper dbHelper;

    public CoinProvider(){}

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CoinDbContract.CONTENT_AUTHORITY, CoinDbContract.PATH_COINS, COINS);
        matcher.addURI(CoinDbContract.CONTENT_AUTHORITY, CoinDbContract.PATH_COIN_SYMBOL, COIN_SYMBOL);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new CoinDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match){
            case COINS:
                return CoinEntry.CONTENT_TYPE;
            case COIN_SYMBOL:
                return  CoinEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final SelectionBuilder builder = buildSelection(uri);

        Timber.d("Query uri: " + uri);

        Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);
        Timber.d("Entering insert method ...");
        //Uri returnUri;

//        switch (match){
//            case COINS: {
//                db.insert(CoinEntry.TABLE_NAME, null, contentValues);
//                returnUri = CoinEntry.CONTENT_URI;
//
//                Timber.d("URI: " + returnUri);
//                break;
//            }
//            default: {
//                throw new UnsupportedOperationException("Unkown Uri: " + uri);
//            }
//        }
//        if (getContext() != null){
//            Timber.d("Context not null");
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//        return returnUri;
        switch (match){
            case COINS: {
                Timber.d("match coins ...");
                final long _id = db.insertOrThrow(CoinEntry.TABLE_NAME, null, contentValues);
                String symbol = contentValues.getAsString(CoinEntry.COLUMN_SYMBOL);
                Timber.d("inserted id: " + _id);

                if (_id > 0 && symbol != null && !symbol.isEmpty()){
                //if (_id > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                    Uri returnUri = CoinEntry.builUriWithSympol(symbol);

                    Timber.d("Inserted uri: " + returnUri.toString());

                    return returnUri;
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case COINS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        db.insert(
                                CoinEntry.TABLE_NAME,
                                null,
                                value
                        );
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                Context context = getContext();
                if (context != null) {
                    context.getContentResolver().notifyChange(uri, null);
                }

                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);

        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).delete(db);
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);

        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).update(db, contentValues);
    }

    private SelectionBuilder buildSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = URI_MATCHER.match(uri);
        return buildSelection(uri, match, builder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
        final List<String> paths = uri.getPathSegments();
        switch (match) {
            case COINS: {
                return builder.table(CoinEntry.TABLE_NAME);
            }
            case COIN_SYMBOL: {
                final String symbol = paths.get(1);
                return builder.table(CoinEntry.TABLE_NAME).where(CoinEntry.COLUMN_SYMBOL + "=?", symbol);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
//    public CoinProvider() {
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        // Implement this to handle requests to delete one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        // TODO: Implement this to handle requests for the MIME type of the data
//        // at the given URI.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        // TODO: Implement this to handle requests to insert a new row.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public boolean onCreate() {
//        // TODO: Implement this to initialize your content provider on startup.
//        return false;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        // TODO: Implement this to handle query requests from clients.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs) {
//        // TODO: Implement this to handle requests to update one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
