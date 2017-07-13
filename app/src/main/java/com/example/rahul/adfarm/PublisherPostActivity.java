package com.example.rahul.adfarm;

        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Message;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.facebook.AccessToken;
        import com.facebook.CallbackManager;
        import com.facebook.FacebookCallback;
        import com.facebook.FacebookException;
        import com.facebook.FacebookSdk;
        import com.facebook.GraphRequest;
        import com.facebook.GraphResponse;
        import com.facebook.login.LoginManager;
        import com.facebook.login.LoginResult;
        import com.facebook.login.widget.LoginButton;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.lang.reflect.Array;
        import java.util.Arrays;

        import static android.R.id.content;
        import static android.R.id.input;
        import static com.example.rahul.adfarm.R.layout.custom_dialog;

public class PublisherPostActivity extends AppCompatActivity {
    private RecyclerView mPromotersList;
    final Context context = this;
    private Button facebookText;
    private Button YoutubeText;
    private CallbackManager callbackManager;
    private RelativeLayout empty_view;
    ProgressDialog dialog;
    FirebaseAuth mAuth;
    private Query mQueryCurrentUser;
    DatabaseReference mRef,mFacebook,mCurrentUser;
    String Uid;

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        callbackManager.onActivityResult(requestCode, responseCode, intent);
//        Intent i = new Intent(PublisherPostActivity.this,PageActivity.class);
//        startActivity(i);
//        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_publisher_post);



        //dialog on opening the activity
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading list please wait...");
        dialog.show();




        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getCurrentUser().getUid();
        empty_view = (RelativeLayout)findViewById(R.id.empty_view);
        mPromotersList = (RecyclerView)findViewById(R.id.promoters_list);
        mPromotersList.setLayoutManager(new LinearLayoutManager(this));

        mRef = FirebaseDatabase.getInstance().getReference();
        mFacebook = mRef.child("users").child("publishers").child("facebook");
        mQueryCurrentUser = mFacebook.orderByChild("Uid").equalTo(Uid);

        mFacebook.addValueEventListener(new ValueEventListener() {
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




        callbackManager = CallbackManager.Factory.create();
        //loginButton = (LoginButton) findViewById(R.id.login_button);

       // View vi = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        //facebookText = (Button) vi.findViewById(R.id.facebook);
        //YoutubeText = (Button) vi.findViewById(R.id.youtube);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(custom_dialog);

                dialog.setTitle("select one platform :");
                Button dialogButton = (Button) dialog.findViewById(R.id.close);
                facebookText = (Button)dialog.findViewById(R.id.facebook);
                YoutubeText = (Button)dialog.findViewById(R.id.youtube);

                facebookText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginManager.getInstance().logInWithReadPermissions(PublisherPostActivity.this, Arrays.asList("email","user_birthday","user_posts","pages_show_list"));
                    }
                });
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                graphRequest(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<FacebookPageInfo,FacebookViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FacebookPageInfo, FacebookViewHolder>(
                FacebookPageInfo.class,
                R.layout.facebook_page_info_blueprint,
                FacebookViewHolder.class,
                mQueryCurrentUser
        ) {
            @Override
            protected void populateViewHolder(FacebookViewHolder viewHolder, FacebookPageInfo model, int position) {

                final String fb_key = getRef(position).getKey();

                viewHolder.setPageName(model.getPageName());
                viewHolder.setPageCategory(model.getPageCategory());
                viewHolder.setFanCount(model.getFanCount());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PublisherPostActivity.this,InterestedAd.class);
                        intent.putExtra("Key",fb_key);
                        startActivity(intent);

                    }
                });
            }
        };

        mPromotersList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FacebookViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public FacebookViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPageName(String pageName) {
            TextView brand_name = (TextView) mView.findViewById(R.id.page_name);
            brand_name.setText(pageName);
        }

        public void setPageCategory(String pageCategory) {
            TextView product_name = (TextView)mView.findViewById(R.id.page_category) ;
            product_name.setText(pageCategory);
        }

        public void setFanCount(String fanCount) {
            TextView category = (TextView)mView.findViewById(R.id.fan_count) ;
            category.setText(fanCount);
        }

    }

    public void graphRequest(AccessToken token){
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //loginButton.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(PublisherPostActivity
                                .this,PageActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();

                    }
                });
    Bundle parameters = new Bundle();
    parameters.putString("fields", "id");
    request.setParameters(parameters);
    request.executeAsync();

}


}
