package com.example.rahul.adfarm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rahul.adfarm.data.AdvertiserContract;

import java.util.ArrayList;

/**
 * Created by Rahul on 5/5/2017.
 */

public class AdvertisementCursorAdapter extends CursorAdapter {
    public AdvertisementCursorAdapter(Context context, Cursor cursor) {
        super(context,cursor, 0);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Check if an existing view is being reused, otherwise inflate the view
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.brand_entry_blueprint, parent, false);
//        }
//
//        Advertisement currentAdvertisement = getItem(position);
//
//        TextView brandName = (TextView) listItemView.findViewById(R.id.brand_name);
//        brandName.setText(currentAdvertisement.getmBrandName());
//
//        TextView productCategory = (TextView) listItemView.findViewById(R.id.product_category);
//        productCategory.setText(currentAdvertisement.getmProductCategory());
//
//
//        TextView productNameText = (TextView) listItemView.findViewById(R.id.product_name);
//        if (currentAdvertisement.hasText()) {
//            TextView productName = (TextView) listItemView.findViewById(R.id.product_name);
//            productName.setText(currentAdvertisement.getmProductName());
//            productNameText.setVisibility(View.VISIBLE);
//        } else {
//            productNameText.setVisibility(View.GONE);
//        }
//
//
//        return listItemView;
//    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.brand_entry_blueprint, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor c) {

        TextView brandName = (TextView) view.findViewById(R.id.brand_name);
        TextView productCategory = (TextView) view.findViewById(R.id.product_category);
        TextView productNameText = (TextView) view.findViewById(R.id.product_name);

        //figure out the index of each column so as to use them easily when required
        int idColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.ID);
        int brandNameColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.BRAND_NAME);
        int firstNameColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.A_FIRST_NAME);
        int lastNameColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.A_LAST_NAME);
        int emailColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.A_EMAIL);
        int productCategoryColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.PRODUCT_CATEGORY);
        int productNameColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.PRODUCT_NAME);
        int platformColumnIndex = c.getColumnIndex(AdvertiserContract.AdvertiserEntry.PLATFORM);

        brandName.setText(c.getString(brandNameColumnIndex));
        productCategory.setText(c.getString(productCategoryColumnIndex));
        productNameText.setText(c.getString(productNameColumnIndex));

    }
}