package com.example.rahul.adfarm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class PageActivity extends Activity {

    DatabaseReference mRef,mChildFb;
    FirebaseAuth mAuth,currentUser;
    String Uid;






    ArrayList<Page> pages = new ArrayList<Page>();
    private  String name,category,fan_count,id;
    PageAdapter boxAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        mRef = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getCurrentUser().getUid();

        fillData();
        boxAdapter = new PageAdapter(this, pages);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(boxAdapter);
    }


    void fillData() {

        Bundle param = new Bundle();
        param.putString("fields", "category,fan_count,name,id");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                param,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject root = new JSONObject(response.getRawResponse());
                            JSONArray data = root.getJSONArray("data");

                            for(int i=0 ; i<data.length() ; i++){

                                JSONObject dataRoot = data.getJSONObject(i);
                                id = dataRoot.getString("id");
                                name = dataRoot.getString("name");
                                category = dataRoot.getString("category");
                                fan_count = dataRoot.getString("fan_count");
                                pages.add(new Page(id, name, category, fan_count, false));

                            }

                            boxAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        }

    public void showResult(View v) {

        for (Page p : boxAdapter.getBox()) {

            if(p.mBox){

                mChildFb = mRef.child("users").child("publishers").child("facebook").child(p.mId);

                String pageName = p.mName;
                String pageCategory = p.mCategory;
                String fanCount = p.mFanCount;

                mChildFb.child("FanCount").setValue(fanCount);
                mChildFb.child("PageCategory").setValue(pageCategory);
                mChildFb.child("PageName").setValue(pageName);
                mChildFb.child("Uid").setValue(Uid);

                Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
        }

    }
}

}


