package com.example.rahul.adfarm;

import android.provider.ContactsContract;

/**
 * Created by Rahul on 5/5/2017.
 */

public class Advertisement {
    private String mBrandName;
    private String mProductName = NO_TEXT_PROVIDED ;
    private String mProductCategory ;
    private static  final String NO_TEXT_PROVIDED = "NO_IMAGE";

    public Advertisement(String brandName, String productCategory, String productName){
        mBrandName = brandName;
        mProductCategory = productCategory;
        mProductName = productName;
    }

    public Advertisement(String brandName, String productCategory){
        mProductCategory = productCategory;
        mBrandName = brandName;
    }

    public String getmBrandName(){return mBrandName;}
    public String getmProductName(){return mProductName;}
    public String getmProductCategory(){return mProductCategory;}

    public boolean hasText() {
        return mProductName != NO_TEXT_PROVIDED;
    }
}
