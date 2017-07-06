package com.example.rahul.adfarm;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.provider.MediaStore;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.meg7.widget.CustomShapeImageView;

        import java.io.File;
public class BrandDescription extends AppCompatActivity {
    private static final int PHOTO_PICKER_ID = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private ImageView attach;
    private Uri imageUri = null;
    //  private AlertDialog dialogg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_description2);



        attach=(ImageView) findViewById(R.id.at);
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater=LayoutInflater.from(BrandDescription.this);
                View subView=inflater.inflate(R.layout.image_dialog,null);

                final AlertDialog.Builder dialog = new AlertDialog.Builder(BrandDescription.this);
                dialog.setTitle("Choose From");
                dialog.setView(subView);

                dialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                final TextView txt_gallry=(TextView)subView.findViewById(R.id.textView_gallery);
                final TextView txt_camera=(TextView)subView.findViewById(R.id.textView_camera);



                txt_gallry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i, PHOTO_PICKER_ID);
                    }
                });
                txt_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        File fil = new File(android.os.Environment.
//                                getExternalStorageDirectory(),"temp.jpg");
                       // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fil));
                        startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE);
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_PICKER_ID && resultCode == RESULT_OK){
            imageUri = data.getData();
            attach.setImageURI(imageUri);
        }

        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == RESULT_OK){
            Bitmap imageCapture = (Bitmap) data.getExtras().get("data");
            attach.setImageBitmap(imageCapture);
        }
    }
}

