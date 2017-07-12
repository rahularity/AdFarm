package com.example.rahul.adfarm;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.adfarm.data.AdvertiserContract;
import com.example.rahul.adfarm.data.AdvertiserHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Rahul on 5/6/2017.
 */

public class AdvertiserPostActivity extends AppCompatActivity{

private RecyclerView mAdvertisementList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String UserId;
    private RelativeLayout empty_view;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.show();
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();

        Toast.makeText(this,UserId,Toast.LENGTH_LONG).show();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("advertisers").child(UserId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    empty_view.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        empty_view = (RelativeLayout)findViewById(R.id.empty_view);
        mAdvertisementList = (RecyclerView)findViewById(R.id.advertisement_list);
     //   mAdvertisementList.setHasFixedSize(true);
        mAdvertisementList.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_add_advertiser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvertiserPostActivity.this, AdvertiserPostForm2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Advertisement,AdvertisementViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Advertisement, AdvertisementViewHolder>(

                Advertisement.class,
                R.layout.brand_entry_blueprint,
                AdvertisementViewHolder.class,
                mDatabase
        ){
            @Override
            protected void populateViewHolder(AdvertisementViewHolder viewHolder, Advertisement model, int position) {

                //this is for getting the random key generated for each post
                final String post_key = getRef(position).getKey();

                viewHolder.setBrandName(model.getBrandName());
                viewHolder.setProductName(model.getProductName());
                viewHolder.setProductCategory(model.getProductCategory());


                viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdvertiserPostActivity.this, AdvertiserPostForm2.class);
                        intent.putExtra("Key",post_key);
                        startActivity(intent);
                    }
                });

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(AdvertiserPostActivity.this,FacebookSuggestionActivity.class);
                        startActivity(i);
                    }
                });

            }
        };

        mAdvertisementList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class AdvertisementViewHolder extends RecyclerView.ViewHolder{

        ImageButton editBtn;
        View mView;

        public AdvertisementViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            editBtn = (ImageButton) mView.findViewById(R.id.edit_button);
        }

        public void setBrandName(String brandName) {
            TextView brand_name = (TextView) mView.findViewById(R.id.brand_name);
            brand_name.setText(brandName);
        }

        public void setProductName(String productName) {
            TextView product_name = (TextView)mView.findViewById(R.id.product_name) ;
            product_name.setText(productName);
        }

        public void setProductCategory(String cat) {
            TextView category = (TextView)mView.findViewById(R.id.product_category) ;
            category.setText(cat);
        }

    }




}

