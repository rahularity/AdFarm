package com.example.rahul.adfarm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.rahul.adfarm.data.AdvertiserContract.*;

/**
 * Created by Rahul on 5/5/2017.
 */

public class AdvertiserHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Advertiser.db";
    public static final int DATABASE_VERSION = 1;

    public AdvertiserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ADVERTISER_TABLE = "CREATE TABLE "+ AdvertiserEntry.TABLE_NAME + "(" +
                AdvertiserEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AdvertiserEntry.A_FIRST_NAME + " TEXT NOT NULL, " +
                AdvertiserEntry.A_LAST_NAME + " TEXT NOT NULL, " +
                AdvertiserEntry.A_EMAIL + " TEXT NOT NULL, " +
                AdvertiserEntry.BRAND_NAME + " TEXT NOT NULL, " +
                AdvertiserEntry.PRODUCT_NAME + " TEXT, " +
                AdvertiserEntry.PRODUCT_CATEGORY + " TEXT NOT NULL, " +
                AdvertiserEntry.PLATFORM + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_ADVERTISER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
