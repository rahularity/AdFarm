package com.example.rahul.adfarm;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InterestedAd extends AppCompatActivity {

    DatabaseReference mInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_ad);

        mInterest = FirebaseDatabase.getInstance().getReference().child("interests");

        //mQueryInterestedAd = mInterest.orderByChild(fb_key).equalTo(Uid);

        String fb_key = getIntent().getStringExtra("Key");


    }
}
