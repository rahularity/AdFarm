package com.example.rahul.adfarm;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {
    private String category, fan_count, name;
    PageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        final ArrayList<Page> pages = new ArrayList<>();

        Bundle param = new Bundle();
        param.putString("fields", "category,fan_count,name");
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
                                name = dataRoot.getString("name");
                                category = dataRoot.getString("category");
                                fan_count = dataRoot.getString("fan_count");

                                pages.add(new Page(name, category, fan_count));

                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        ListView pageListView = (ListView) findViewById(R.id.list);
        adapter = new PageAdapter(this, pages);
        pageListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

