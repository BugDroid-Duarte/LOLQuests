package com.example.bugdroid.lolquests.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.bugdroid.lolquests.BD.QuestsHelper;
import com.example.bugdroid.lolquests.GameHistory;
import com.example.bugdroid.lolquests.Objects.Quest;
import com.example.bugdroid.lolquests.Outros.AlarmReceiver;
import com.example.bugdroid.lolquests.Outros.NegativeViewDialog;
import com.example.bugdroid.lolquests.Outros.ViewDialog;
import com.example.bugdroid.lolquests.R;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.game.Game;
import com.robrua.orianna.type.exception.APIException;

import java.util.List;

public class InitialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context appContext;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Game selectedGame;
    private static final String TAG = "teste";
    private static final String TAG2 = "teste2";
    QuestsHelper db = new QuestsHelper(this);
    public static TextView desc;
    public static TextView status;
    public static TextView csobjective;
    public static TextView cscompare;
    public static TextView exp;
    public EditText search;
    public Button button;
    public final Long cs = null; // minions farmados
    public Long csjogo = null; // minions farmados
    private Button verify;
    public static Quest newQuest = new Quest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appContext=getApplicationContext();

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences shareprefs = getSharedPreferences("cscs", MODE_PRIVATE);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        startAlarm();

        verify = (Button) findViewById(R.id.verify);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InitialActivity.this, "A verificar ...", Toast.LENGTH_SHORT).show();
                verifyQUEST(sharedPref.getString("username", ""), shareprefs.getLong("cscs",0));
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        desc = (TextView) findViewById(R.id.desc);
        status = (TextView) findViewById(R.id.status);
        exp = (TextView) findViewById(R.id.exp);
        search = (EditText) findViewById(R.id.search);
        button = (Button) findViewById(R.id.button);

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
        int interval = 5000;
                //86400000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
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
                            Log.d(TAG,String.valueOf(csjogo) );
                            ViewDialog alert = new ViewDialog();
                            alert.showDialog(InitialActivity.this, "Congratulations, quest completed !", csquest, csjogo);
                            //Log.d(TAG, "run: QUEST COMPLETED");
                        } else {
                            Log.d(TAG, "run: QUEST FAILED");
                            NegativeViewDialog alert = new NegativeViewDialog();
                            alert.showDialog(InitialActivity.this, "What a noob, you failed the quest !", csquest, csjogo);
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
            // Handle the camera action
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(InitialActivity.this, ProfileQuest.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(InitialActivity.this, GameHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_board) {

        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(InitialActivity.this, UserSettings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
