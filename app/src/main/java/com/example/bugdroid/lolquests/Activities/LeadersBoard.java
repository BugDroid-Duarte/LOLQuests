package com.example.bugdroid.lolquests.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bugdroid.lolquests.Outros.HttpHandler;
import com.example.bugdroid.lolquests.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class LeadersBoard extends AppCompatActivity {

    String myJSON;
    private static String url = "https://lolquestsdb.000webhostapp.com/android_api_login/leaders.php";
    JSONArray users = null;
    ArrayList<HashMap<String, String>> usersList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders_board);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>LeaderBoard</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.listView);

        usersList = new ArrayList<HashMap<String, String>>();

        new GetLeaders().execute();
    }

    private class GetLeaders extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "teste";

        @Override
        protected void onPreExecute() {
            list.setAdapter(null);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... Void) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            JSONArray users = new JSONArray();

            try {
                users = new JSONArray(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonStr != null) {
                try {
                    // looping through All
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String nickname = c.getString("nickname");
                        String points = c.getString("points");

                        // tmp hash map for single contact
                        HashMap<String, String> user = new HashMap<>();

                        // adding each child node to HashMap key => value
                        user.put("Id", id);
                        user.put("name", name);
                        user.put("nickname", nickname);
                        user.put("points", points);

                        usersList.add(user);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();

                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                " Está ligado á internet ?",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }
        
        @Override
        protected void onPostExecute (Void result){
            super.onPostExecute(result);

                ListAdapter adapter = new SimpleAdapter(
                        LeadersBoard.this, usersList,
                        R.layout.list_item, new String[]{"name", "nickname",
                        "points"}, new int[]{R.id.name, R.id.nick,
                        R.id.points, });

                list.setAdapter(adapter);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
