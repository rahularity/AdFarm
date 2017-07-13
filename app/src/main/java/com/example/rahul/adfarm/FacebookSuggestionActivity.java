package com.example.rahul.adfarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacebookSuggestionActivity extends AppCompatActivity {
    private DatabaseReference mRef,mFb,mInterest;
    private RecyclerView mFbPageList;
    private RelativeLayout empty_view;
    boolean mInterested = false;
    private String post_key = null;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_suggestion);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.show();
        empty_view = (RelativeLayout)findViewById(R.id.empty_view);

        mFbPageList = (RecyclerView) findViewById(R.id.mFbPageList);
        mFbPageList.setLayoutManager(new LinearLayoutManager(this));
        mRef = FirebaseDatabase.getInstance().getReference();
        mFb = mRef.child("users").child("publishers").child("facebook");
        mInterest = FirebaseDatabase.getInstance().getReference().child("interests");
        mInterest.keepSynced(true);
        mFb.keepSynced(true);
        mRef.keepSynced(true);


        mFb.addValueEventListener(new ValueEventListener() {
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


        post_key = getIntent().getStringExtra("Key");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Page2,SuggestionViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Page2,SuggestionViewHolder>(
                Page2.class,
                R.layout.facebook_suggestion_blueprint,
                SuggestionViewHolder.class,
                mFb
        ){
            @Override
            protected void populateViewHolder(SuggestionViewHolder viewHolder, final Page2 model, final int position) {
               // final String post_key = getRef(position).getKey();

                final String fb_key = getRef(position).getKey();
                viewHolder.setPageName(model.getPageName());
                viewHolder.setPageCategory(model.getPageCategory());
                viewHolder.setFanCount(model.getFanCount());

                viewHolder.setInterestButton(fb_key,post_key);

                viewHolder.interestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mInterested = true;



                            mInterest.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if(mInterested) {

                                        if (dataSnapshot.child(fb_key).hasChild(post_key)) {

                                            mInterest.child(fb_key).child(post_key).removeValue();
                                            mInterested = false;

                                        } else {

                                            mInterest.child(fb_key).child(post_key).setValue("rahul");
                                            mInterested = false;

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                    }
                });
            }


        };

        mFbPageList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SuggestionViewHolder extends RecyclerView.ViewHolder{

        DatabaseReference mInterest;

        ImageButton interestBtn;
        View mView;



        public SuggestionViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            interestBtn = (ImageButton) mView.findViewById(R.id.interest_btn);
            mInterest = FirebaseDatabase.getInstance().getReference().child("interests");
            mInterest.keepSynced(true);
        }

        public void setInterestButton(final String fb_key, final String post_key){

            mInterest.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(fb_key).hasChild(post_key)){

                        interestBtn.setImageResource(R.drawable.heart_filled);

                    }else{

                        interestBtn.setImageResource(R.drawable.heart_blank);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setPageName(String pageName) {
            TextView page_name = (TextView) mView.findViewById(R.id.page_name);
            page_name.setText(pageName);
        }

        public void setPageCategory(String pageCategory) {
            TextView page_category = (TextView)mView.findViewById(R.id.page_category) ;
            page_category.setText(pageCategory);
        }

        public void setFanCount(String fan) {
            TextView category = (TextView)mView.findViewById(R.id.fan_count) ;
            category.setText(fan);
        }

    }
}
