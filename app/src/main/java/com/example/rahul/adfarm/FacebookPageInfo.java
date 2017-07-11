package com.example.rahul.adfarm;

/**
 * Created by Rahul on 7/10/2017.
 */

public class FacebookPageInfo {

    private String PageName;
    private String PageCategory;
    private String FanCount;

    public FacebookPageInfo(){}

    public FacebookPageInfo(String PageName, String PageCategory, String FanCount) {
        this.PageName = PageName;
        this.PageCategory = PageCategory;
        this.FanCount = FanCount;
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }

    public String getPageCategory() {
        return PageCategory;
    }

    public void setPageCategory(String pageCategory) {
        PageCategory = pageCategory;
    }

    public String getFanCount() {
        return FanCount;
    }

    public void setFanCount(String fanCount) {
        FanCount = fanCount;
    }
}
