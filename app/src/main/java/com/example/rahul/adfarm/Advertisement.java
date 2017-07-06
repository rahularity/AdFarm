package com.example.rahul.adfarm;

import android.provider.ContactsContract;

/**
 * Created by Rahul on 5/5/2017.
 */

public class Advertisement {
    private String BrandName;
    private String ProductName;
    private String ProductCategory ;

    public Advertisement(){}
    public Advertisement(String BrandName, String ProductCategory, String ProductName){
        this.BrandName = BrandName;
        this.ProductCategory =ProductCategory;
        this.ProductName = ProductName;
    }


    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }


}
