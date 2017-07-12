package com.example.rahul.adfarm;

/**
 * Created by Rahul on 7/12/2017.
 */

public class Page2 {
    String PageName,PageCategory,FanCount;
    public Page2(){}

    public Page2(String PageName, String PageCategory, String FanCount) {
        this.PageName = PageName;
        this.PageCategory = PageCategory;
        this.FanCount = FanCount;
    }

    public String getPageName() {
        return PageName;
    }

    public String getPageCategory() {
        return PageCategory;
    }

    public String getFanCount() {
        return FanCount;
    }
}
