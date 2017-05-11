package com.example.rahul.adfarm;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.SimpleCursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.rahul.adfarm.data.AdvertiserContract;
import com.example.rahul.adfarm.data.AdvertiserHelper;

import java.util.ArrayList;

/**
 * Created by Rahul on 5/6/2017.
 */

public class AdvertiserPostActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ArrayList<Advertisement> advertisement;
    AdvertiserHelper advertiserHelper;
    Context context = this;
    AdvertisementCursorAdapter mAdvertisementCursorAdapter;
    private static final int ADVERTISEMENT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

          advertisement = new ArrayList<>();
//        advertisement.add(new Advertisement("Adidas","Footwears"));
//        advertisement.add(new Advertisement("Puma","Sports","Cricket Bat"));
//        advertisement.add(new Advertisement("Kukaburra","Sports","Cricket Ball"));

//        AdvertisementAdapter adapter = new AdvertisementAdapter(this, advertisement);
//        ListView advertisementListView = (ListView)findViewById(R.id.advertisement_list);
//        advertisementListView.setAdapter(adapter);

//        String[] projection = {
//                AdvertiserContract.AdvertiserEntry.ID,
//                AdvertiserContract.AdvertiserEntry.BRAND_NAME, AdvertiserContract.AdvertiserEntry.PRODUCT_CATEGORY,
//                AdvertiserContract.AdvertiserEntry.PRODUCT_NAME
//        };
//
//        advertiserHelper = new AdvertiserHelper(this);
//        Cursor cursor = getContentResolver().query(AdvertiserContract.CONTENT_URI, projection, null, null, null);

        ListView advertisementListView = (ListView)findViewById(R.id.advertisement_list);
//        AdvertisementCursorAdapter advertisementCursorAdapter = new AdvertisementCursorAdapter(this, cursor);
//        advertisementListView.setAdapter(advertisementCursorAdapter);



        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_add_advertiser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdvertiserPostForm.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView petListView = (ListView) findViewById(R.id.advertisement_list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);


        mAdvertisementCursorAdapter = new AdvertisementCursorAdapter(this,null);
        advertisementListView.setAdapter(mAdvertisementCursorAdapter);
        getLoaderManager().initLoader(ADVERTISEMENT_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        String[] projection = {
                AdvertiserContract.AdvertiserEntry.ID,
                AdvertiserContract.AdvertiserEntry.BRAND_NAME, AdvertiserContract.AdvertiserEntry.PRODUCT_CATEGORY,
                AdvertiserContract.AdvertiserEntry.PRODUCT_NAME
        };

        switch (id){
            case ADVERTISEMENT_LOADER:
                return new CursorLoader(context,
                        AdvertiserContract.AdvertiserEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdvertisementCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdvertisementCursorAdapter.swapCursor(null);
    }
}
