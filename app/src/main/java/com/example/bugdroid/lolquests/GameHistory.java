package com.example.bugdroid.lolquests;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.game.Game;
import com.robrua.orianna.type.exception.APIException;

import java.util.List;

public class GameHistory extends Activity {

    private static final String TAG = "testenull";
    public static ListView matchHistory; //listview of matches
    public static GameAdapter gameListAdapter;
    public String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        AsyncRiotAPI.setRegion(Region.EUW);
        AsyncRiotAPI.setAPIKey("RGAPI-06d9fe30-edbf-4d8b-a025-1c0a5de907a9");

        matchHistory = (ListView) findViewById(R.id.history);

        nick = sharedPref.getString("username", "");

        Log.d(TAG, String.valueOf(nick));

        new Thread(new Runnable(){
            @Override
            public void run(){
                getGameHistory(nick);
            }
        }).start();
    }


    public void getGameHistory(String nick) {
        AsyncRiotAPI.getRecentGames(new Action<List<Game>>() {

        @Override
        public void perform(final List<Game> games) {

            runOnUiThread(new Runnable() {
                public void run() {
                    gameListAdapter = new GameAdapter(GameHistory.this, games);
                    matchHistory.setAdapter(gameListAdapter);
                }
            });
        }

        @Override
        public void handle(APIException e) {
            Log.d(TAG, "ERRO, RIP");
        }
         }, nick );
    }
}


