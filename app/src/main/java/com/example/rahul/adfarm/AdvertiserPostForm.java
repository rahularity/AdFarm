package com.example.rahul.adfarm;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.adfarm.data.AdvertiserContract;
import com.example.rahul.adfarm.data.AdvertiserHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdvertiserPostForm extends AppCompatActivity {

    Context context = this;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText brandName;
    private EditText productName;
    private EditText productCategory;
    private AdvertiserHelper advertiserHelper;
    private Spinner platform;
    private int mPlatform = AdvertiserContract.AdvertiserEntry.PLATFORM_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_post_form);

        //Displaying TextInputLayout Error
//        TextInputLayout lNameLayout = (TextInputLayout) findViewById(R.id.lNameLayout);
//        lNameLayout.setErrorEnabled(true);
//        lNameLayout.setError("Min 2 chars required");
//
//        //Displaying EditText Error
//        EditText age = (EditText) findViewById(R.id.age);
//        age.setError("Required");
//
//        //Displaying both TextInputLayout and EditText Errors
//        TextInputLayout phoneLayout = (TextInputLayout) findViewById(R.id
//                .phoneLayout);
//        phoneLayout.setErrorEnabled(true);
//        phoneLayout.setError("Please enter a phone number");
//        EditText phone = (EditText) findViewById(R.id.phone);
//        phone.setError("Required");

        advertiserHelper = new AdvertiserHelper(this);

        //finding views from the layout by ID
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email = (EditText)findViewById(R.id.email);
        brandName = (EditText)findViewById(R.id.brandName);
        productName = (EditText)findViewById(R.id.productName);
        productCategory =(EditText)findViewById(R.id.category);
        platform = (Spinner)findViewById(R.id.platform);
        setupSpinner();


        Button save_button = (Button)findViewById(R.id.save_advertisement);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAdvertisementInDatabase();
                finish();
            }
        });

        TextView addBrandDescription = (TextView)findViewById(R.id.add_brand_description);
        addBrandDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,BrandDescription.class);
                startActivity(i);
            }
        });
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
                        mPlatform = AdvertiserContract.AdvertiserEntry.PLATFORM_FACEBOOK;
                    } else if (selection.equals(getString(R.string.platform_youtube))) {
                        mPlatform = AdvertiserContract.AdvertiserEntry.PLATFORM_YOUTUBE;
                    }else if (selection.equals(getString(R.string.platform_instagram))){
                        mPlatform = AdvertiserContract.AdvertiserEntry.PLATFORM_INTAGRAM;
                    } else {
                        mPlatform = AdvertiserContract.AdvertiserEntry.PLATFORM_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPlatform = AdvertiserContract.AdvertiserEntry.PLATFORM_UNKNOWN;
            }
        });
    }

    public void saveAdvertisementInDatabase(){
        String advertiserFirstName = firstName.getText().toString().trim();
        String advertiserLastName = lastName.getText().toString().trim();
        String advertiserEmail = email.getText().toString().trim();
        String advertiserBrandName = brandName.getText().toString().trim();
        String advertiserProductCategory = productCategory.getText().toString().trim();
        String advertiserProductName = productName.getText().toString().trim();


        ContentValues values = new ContentValues();

        values.put(AdvertiserContract.AdvertiserEntry.A_FIRST_NAME,advertiserFirstName);
        values.put(AdvertiserContract.AdvertiserEntry.A_LAST_NAME,advertiserLastName);
        values.put(AdvertiserContract.AdvertiserEntry.A_EMAIL,advertiserEmail);
        values.put(AdvertiserContract.AdvertiserEntry.BRAND_NAME,advertiserBrandName);
        values.put(AdvertiserContract.AdvertiserEntry.PRODUCT_CATEGORY,advertiserProductCategory);
        values.put(AdvertiserContract.AdvertiserEntry.PRODUCT_NAME,advertiserProductName);
        values.put(AdvertiserContract.AdvertiserEntry.PLATFORM,mPlatform);

        Uri newUri = getContentResolver().insert(AdvertiserContract.CONTENT_URI, values);


        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this,"failed to add new post",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "successful insertion of post",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
