package com.example.bugdroid.lolquests.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bugdroid.lolquests.BD.QuestsHelper;
import com.example.bugdroid.lolquests.BD.SessionManager;
import com.example.bugdroid.lolquests.Objects.Quest;
import com.example.bugdroid.lolquests.Outros.AlarmReceiver;
import com.example.bugdroid.lolquests.Outros.AppConfig;
import com.example.bugdroid.lolquests.Outros.NegativeViewDialog;
import com.example.bugdroid.lolquests.Outros.ViewDialog;
import com.example.bugdroid.lolquests.R;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.game.Game;
import com.robrua.orianna.type.exception.APIException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context appContext;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Game selectedGame;
    private SessionManager session;
    private static final String TAG = "teste";
    private static final String TAG2 = "teste2";
    public static TextView desc;
    public static TextView status;
    public static TextView exp;
    public EditText search;
    public Button button;
    public Long csjogo = null; // minions farmados
    public static Quest newQuest = new Quest();

    String URL = "https://lolquestsdb.000webhostapp.com/android_api_login/insertpoints.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appContext=getApplicationContext();

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences shareprefs = getSharedPreferences("cscs", MODE_PRIVATE);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences DadosNewQuest = PreferenceManager.getDefaultSharedPreferences(this);

        session = new SessionManager(getApplicationContext());

        desc = (TextView) findViewById(R.id.desc);
        status = (TextView) findViewById(R.id.status);
        exp = (TextView) findViewById(R.id.exp);
        search = (EditText) findViewById(R.id.search);
        button = (Button) findViewById(R.id.button);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        if (!prefs.getBoolean("firstTime", false)) {
            Log.d(TAG, "onCreate: CORREU");
            startAlarm();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        desc.setText(DadosNewQuest.getString("desc", ""));
        status.setText(String.valueOf(DadosNewQuest.getInt("status", 0)));
        exp.setText(String.valueOf(DadosNewQuest.getInt("exp", 0)));

        Log.d("lul", DadosNewQuest.getString("desc", ""));
        Log.d("lul", String.valueOf(DadosNewQuest.getInt("status", 0)));
        Log.d("lul", String.valueOf(DadosNewQuest.getInt("exp", 0)));

        MainActivity.status.setText(String.valueOf(newQuest.getProgress()));
        MainActivity.exp.setText(String.valueOf(newQuest.getExp()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "A verificar ...", Toast.LENGTH_SHORT).show();
                verifyQUEST(sharedPref.getString("username", ""), shareprefs.getLong("cscs",0));
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        AsyncRiotAPI.setRegion(Region.EUW);
        AsyncRiotAPI.setAPIKey("RGAPI-06d9fe30-edbf-4d8b-a025-1c0a5de907a9");

        Log.d(TAG2, String.valueOf(shareprefs.getLong("cscs",0)));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void startAlarm() {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 30000;
                //86400000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    public void cancelAlarm() {
        if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyQUEST(String nick, final long csquest) {
        AsyncRiotAPI.getRecentGames(new Action<List<Game>>() {
            @Override
            public void perform(final List<Game> games) {

                runOnUiThread(new Runnable() {
                    public void run() {

                        selectedGame = games.get(0);
                        csjogo = (long) selectedGame.getStats().getMinionsKilled() + selectedGame.getStats().getNeutralMinionsKilledEnemyJungle() + selectedGame.getStats().getNeutralMinionsKilledYourJungle();
                        Log.d(TAG, String.valueOf(csjogo));

                        if (csjogo >= csquest ) {
                            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            ViewDialog alert = new ViewDialog();
                            alert.showDialog(MainActivity.this, "Congratulations, quest completed !", csquest, csjogo);

                            int UpdatedPoints = sharedPref.getInt("points", 0) + newQuest.getExp();

                            addPoints(UpdatedPoints, sharedPref.getString("username", ""));
                        } else {
                            NegativeViewDialog alert = new NegativeViewDialog();
                            alert.showDialog(MainActivity.this, "What a noob, you failed the quest !", csquest, csjogo);
                        }
                    }
                });
            }
            @Override
            public void handle(APIException e) {
                Log.d(TAG, "ERRO, RIP");
            }
        }, nick );
    }

    private void addPoints (final int points, final String nickname) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("points", String.valueOf(points));
                params.put("nickname", nickname);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            if ( session.isLoggedIn()) {
                Toast.makeText(appContext, "You are already logged in !", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileQuest.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, QuestsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_board) {

        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_logout) {
            if (session.isLoggedIn()) {
                session.setLogin(false);
            }
            Toast.makeText(appContext, "logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, UserSettings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}