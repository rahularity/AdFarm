package com.example.rahul.adfarm.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.security.PublicKey;

/**
 * Created by Rahul on 5/5/2017.
 */

public final class AdvertiserContract {
    private AdvertiserContract(){}

    public static final class AdvertiserEntry implements BaseColumns{
        public static final String ID = BaseColumns._ID;
        public static final String TABLE_NAME = "advertiser_table";
        public static final String A_FIRST_NAME = "advertiser_first_name";
        public static final String A_LAST_NAME = "advertiser_last_name";
        public static final String A_EMAIL = "advertiser_email";
        public static final String BRAND_NAME = "brand_name";
        public static final String PRODUCT_NAME = "product_name";
        public static final String PRODUCT_CATEGORY = "product_category";
        public static final String PLATFORM = "product_platform";

        public static final int PLATFORM_UNKNOWN = 0;
        public static final int PLATFORM_FACEBOOK = 1;
        public static final int PLATFORM_YOUTUBE = 2;
        public static final int PLATFORM_INTAGRAM = 3;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ADVERTISER);
        }

        public static  final String CONTENT_AUTHORITY = "com.example.rahul.adfarm";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //Table name
    public static final String PATH_ADVERTISER = "advertiser_table";
    //Complete CONTENT_URI
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ADVERTISER);


}

