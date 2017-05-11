package com.example.rahul.adfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class UserSelectionActivity extends AppCompatActivity {
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);
        textView1=(TextView)findViewById(R.id.tvAbout);
        textView2=(TextView)findViewById(R.id.tvAbout1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSelectionActivity.this, AdvertiserPostActivity.class);
                startActivity(intent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSelectionActivity.this, PublisherPostActivity.class);
                startActivity(intent);
            }
        });


    }
}
