package com.example.rahul.adfarm;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Message;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.PopupWindow;
        import android.widget.Toast;

        import com.github.clans.fab.FloatingActionButton;
        import com.github.clans.fab.FloatingActionMenu;

        import com.github.clans.fab.FloatingActionMenu;

        import static android.R.id.input;

public class PublisherPostActivity extends AppCompatActivity {
    private FloatingActionMenu fam;
    private FloatingActionButton fabTube,fabins,fabfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_post);

        fabfb=(FloatingActionButton)findViewById(R.id.fab2);
        fabins=(FloatingActionButton)findViewById(R.id.fab3);
        fabTube=(FloatingActionButton)findViewById(R.id.fab1);
        fam=(FloatingActionMenu)findViewById(R.id.fab_menu);

        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                   // showToast("Menu is opened");
                } else {
                   // showToast("Menu is closed");
                }
            }
        });
        //handling each floating action button clicked
        fabins.setOnClickListener(onButtonClick());
        fabTube.setOnClickListener(onButtonClick());
        fabfb.setOnClickListener(onButtonClick());

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });
        fabfb.setOnClickListener(new View.OnClickListener() {
            //@SuppressWarnings("deprecation")
            public void onClick(final View view) {
                LayoutInflater inflater=LayoutInflater.from(PublisherPostActivity.this);
                View subView=inflater.inflate(R.layout.alert_dialog,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(PublisherPostActivity.this);
                builder.setView(subView);
                builder.setTitle("Link for page to enter");
                //final EditText input = new EditText(MainActivity.this);
                final EditText link=(EditText)subView.findViewById(R.id.link);
                link.setText("www.facebook.com/");
                link.setSelection(link.getText().length());
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                // Setting Negative "NO" Button
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                builder.show();
            }
        });

        fabins.setOnClickListener(new View.OnClickListener() {
            //@SuppressWarnings("deprecation")
            public void onClick(final View view) {
                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PublisherPostActivity.this);
                // AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                //Setting Dialog Message
                alertDialog.setMessage("Link for page to enter");
                final EditText input = new EditText(PublisherPostActivity.this);
                input.setText("www.Instagram.com/");
                input.setSelection(input.getText().length());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setView(input);
//                 Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(),"Password Matched", Toast.LENGTH_SHORT).show();
                                Intent myIntent1 = new Intent(view.getContext(),PublisherPostActivity.class);
                                startActivityForResult(myIntent1, 0);
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
            }
        });
        fabTube.setOnClickListener(new View.OnClickListener() {
            //@SuppressWarnings("deprecation")
            public void onClick(final View view) {
                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PublisherPostActivity.this);
                // AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                //Setting Dialog Message
                alertDialog.setMessage("Link for page to enter");
                final EditText input = new EditText(PublisherPostActivity.this);
                input.setText("www.youtube.com/");
                input.setSelection(input.getText().length());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setView(input);
//                 Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(),"Password Matched", Toast.LENGTH_SHORT).show();
                                Intent myIntent1 = new Intent(view.getContext(),PublisherPostActivity.class);
                                startActivityForResult(myIntent1, 0);
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
            }
        });
    }
    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabfb) {
                    //showToast("Button Add clicked");
                } else if (view == fabins) {
                    //showToast("Button Delete clicked");
                } else {
                    //showToast("Button Edit clicked");
                }
                fam.close(true);
            }
        };
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
