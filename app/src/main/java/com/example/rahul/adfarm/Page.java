package com.example.rahul.adfarm;

/**
 * Created by Rahul on 6/27/2017.
 */

public class Page {
    private String mName;
    private String mCategory;
    private String mFanCount;

    public Page(String name, String category, String fanCount){
        mName = name;
        mCategory = category;
        mFanCount = fanCount;
    }

    public String getmName(){return mName;}
    public String getmCategory(){return mCategory;}
    public String getmFanCount(){return mFanCount;}
}

