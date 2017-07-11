package com.example.rahul.adfarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.adfarm.data.AdvertiserContract;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

public class AdvertiserPostForm2 extends AppCompatActivity {

    private static final int MAX_LENGTH = 15;
    Context context = this;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText brandName;
    private EditText productName;
    private EditText productCategory;
    private Button saveBtn;
    private FirebaseStorage mFirebaseStorage;
    private DatabaseReference mRef,mAd,mRefUser,newPost,mAdEdit;
    private FirebaseAuth mAuth,currentUser;
    private String descText,post_key = null;
    private static final int PHOTO_PICKER_ID = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private ImageView attach;
    private Uri uri,imageUrl;
    private Uri imageUri = null;
    private EditText et;
    ProgressDialog mProgress_save_to_database , mProgress_get_from_database;
    StorageReference mStorage,childStorage;
    private Spinner platform;
    private String mPlatform = "Select";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_post_form2);

        mProgress_save_to_database = new ProgressDialog(this);
        mProgress_get_from_database = new ProgressDialog(this);



        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String UserID = mAuth.getCurrentUser().getUid();
        //when intent called for editing the changes
        post_key = getIntent().getStringExtra("Key");
//        Toast.makeText(this,post_key,Toast.LENGTH_LONG).show();

        if(post_key != null){

            mAdEdit = FirebaseDatabase.getInstance().getReference().child("users")
                    .child("advertisers")
                    .child(UserID)
                    .child(post_key);

            mAdEdit.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    firstName.setText(dataSnapshot.child("FirstName").getValue().toString());
                    lastName.setText(dataSnapshot.child("LastName").getValue().toString());
                    email.setText(dataSnapshot.child("Email").getValue().toString());
                    brandName.setText(dataSnapshot.child("BrandName").getValue().toString());
                    productName.setText(dataSnapshot.child("ProductName").getValue().toString());
                    productCategory.setText(dataSnapshot.child("ProductCategory").getValue().toString());
                    et.setText(dataSnapshot.child("Description").getValue().toString());
                    if(dataSnapshot.child("ImageUrl").getValue().toString()!=null){
                        Picasso.with(context).load(dataSnapshot.child("ImageUrl").getValue().toString()).into(attach);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(context,"Something went wrong... Try again",Toast.LENGTH_LONG).show();

                }
            });
        }



        mRef = FirebaseDatabase.getInstance().getReference();
        mRefUser = mRef.child("users");
        mAd = mRefUser.child("advertisers").child(UserID);
        newPost = mAd.push();
        childStorage = mStorage.child("pics").child(randomize());


        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email = (EditText)findViewById(R.id.email);
        brandName = (EditText)findViewById(R.id.brandName);
        productName = (EditText)findViewById(R.id.productName);
        productCategory =(EditText)findViewById(R.id.category);
        platform = (Spinner)findViewById(R.id.platform);
        saveBtn = (Button)findViewById(R.id.save_advertisement) ;
        et = (EditText)findViewById(R.id.et);
        attach=(ImageView) findViewById(R.id.at);


        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, PHOTO_PICKER_ID);
            }
        });



        setupSpinner();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress_save_to_database.setMessage("saving data...");
                mProgress_save_to_database.show();
                saveToFirebase();
            }
        });

    }

    private void saveToFirebase() {
        String fn = firstName.getText().toString().trim();
        String ln = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String brand = brandName.getText().toString().trim();
        String product = productName.getText().toString().trim();
        String category = productCategory.getText().toString().trim();
        descText = et.getText().toString().trim();


        newPost.child("FirstName").setValue(fn);
        newPost.child("LastName").setValue(ln);
        newPost.child("Email").setValue(mail);
        newPost.child("BrandName").setValue(brand);
        newPost.child("ProductName").setValue(product);
        newPost.child("ProductCategory").setValue(category);
        newPost.child("Platform").setValue(mPlatform);
        newPost.child("Description").setValue(descText);
        childStorage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdvertiserPostForm2.this,"upload done",Toast.LENGTH_LONG).show();
                imageUrl = taskSnapshot.getDownloadUrl();
                newPost.child("ImageUrl").setValue(imageUrl.toString());
                mProgress_save_to_database.dismiss();
                finish();
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

    public static String randomize() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_PICKER_ID && resultCode == RESULT_OK){

            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                attach.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

//        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == RESULT_OK){
//            Bitmap imageCapture = (Bitmap) data.getExtras().get("data");
//            attach.setImageBitmap(imageCapture);
//        }
    }
}
