package com.example.rahul.adfarm.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Rahul on 5/9/2017.
 */

public class AdvertiserProvider extends ContentProvider {
    private static final int ADVERTISER_TABLE = 100;
    private static final int ADVERTISER_ROW = 101;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        sUriMatcher.addURI(AdvertiserContract.CONTENT_AUTHORITY, AdvertiserContract.PATH_ADVERTISER,ADVERTISER_TABLE);
        sUriMatcher.addURI(AdvertiserContract.CONTENT_AUTHORITY,AdvertiserContract.PATH_ADVERTISER + "/#",ADVERTISER_ROW);
    }

    private AdvertiserHelper mAdvertiserHelper;
    public static final String LOG_TAG = AdvertiserProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        // TODO: Create and initialize a PetDbHelper object to gain access to the pets database.
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        mAdvertiserHelper = new AdvertiserHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mAdvertiserHelper.getReadableDatabase() ;
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case ADVERTISER_TABLE:
                cursor = database.query(AdvertiserContract.AdvertiserEntry.TABLE_NAME,projection,selection,selectionArgs,null, null, sortOrder);
                break;

            case ADVERTISER_ROW:
                selection = AdvertiserContract.AdvertiserEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(AdvertiserContract.AdvertiserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADVERTISER_TABLE:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {

        // TODO: Insert a new pet into the pets database table with the given ContentValues

        SQLiteDatabase database = mAdvertiserHelper.getWritableDatabase();
        long id = database.insert(AdvertiserContract.AdvertiserEntry.TABLE_NAME,null,values);
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
