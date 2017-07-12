package com.example.rahul.adfarm;

/**
 * Created by Rahul on 6/27/2017.
 */

public class Page {
     String mId;
     String mName;
     String mCategory;
     String mFanCount;
     boolean mBox;

    public Page(String id, String name, String category, String fanCount, boolean box){
        mId = id;
        mName = name;
        mCategory = category;
        mFanCount = fanCount;
        mBox = box;
    }
}

