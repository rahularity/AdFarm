package com.example.rahul.adfarm;

        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Message;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
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

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.lang.reflect.Array;
        import java.util.Arrays;

        import static android.R.id.content;
        import static android.R.id.input;
        import static com.example.rahul.adfarm.R.layout.custom_dialog;

public class PublisherPostActivity extends AppCompatActivity {
    final Context context = this;
    private Button facebookText;
    private Button YoutubeText;
    private CallbackManager callbackManager;

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
