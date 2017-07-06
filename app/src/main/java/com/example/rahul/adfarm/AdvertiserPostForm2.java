package com.example.rahul.adfarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rahul.adfarm.data.AdvertiserContract;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class AdvertiserPostForm2 extends AppCompatActivity {

    Context context = this;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText brandName;
    private EditText productName;
    private EditText productCategory;
    private Button brandDescriptionBtn,saveBtn;
    private FirebaseStorage mFirebaseStorage;
    private DatabaseReference mRef,mAd,mRefUser,mPb;
    private FirebaseAuth mAuth;
    ProgressDialog mProgress;

    private Spinner platform;

    private String mPlatform = "Select";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_post_form2);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email = (EditText)findViewById(R.id.email);
        brandName = (EditText)findViewById(R.id.brandName);
        productName = (EditText)findViewById(R.id.productName);
        productCategory =(EditText)findViewById(R.id.category);
        platform = (Spinner)findViewById(R.id.platform);
        brandDescriptionBtn = (Button)findViewById(R.id.add_brand_description);
        saveBtn = (Button)findViewById(R.id.save_advertisement) ;




        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        String UserID = mAuth.getCurrentUser().getUid();


        mRef = FirebaseDatabase.getInstance().getReference();
        mRefUser = mRef.child("users").child(UserID);
        mAd = mRefUser.child("advertisers");
        mPb = mRefUser.child("publishers");

        setupSpinner();

        brandDescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdvertiserPostForm2.this,BrandDescription.class);
                startActivity(i);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase();
                finish();
            }
        });

    }

    private void saveToFirebase() {

        mProgress.setMessage("uploading to server...");
        mProgress.show();
        String fn = firstName.getText().toString().trim();
        String ln = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String brand = brandName.getText().toString().trim();
        String product = productName.getText().toString().trim();
        String category = productCategory.getText().toString().trim();

        DatabaseReference newPost = mAd.push();
        newPost.child("FirstName").setValue(fn);
        newPost.child("LastName").setValue(ln);
        newPost.child("Email").setValue(mail);
        newPost.child("BrandName").setValue(brand);
        newPost.child("ProductName").setValue(product);
        newPost.child("ProductCategory").setValue(category);
        newPost.child("Platform").setValue(mPlatform);

        mProgress.dismiss();


    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_platform_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        platform.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        platform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.platform_facebook))) {
                        mPlatform = "Facebook";
                    } else if (selection.equals(getString(R.string.platform_youtube))) {
                        mPlatform = "Youtube";
                    }else if (selection.equals(getString(R.string.platform_instagram))){
                        mPlatform = "Instagram";
                    } else {
                        mPlatform = "Select";
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPlatform = "Select";
            }
        });
    }
}
