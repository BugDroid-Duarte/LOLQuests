package com.example.bugdroid.lolquests.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bugdroid.lolquests.BD.QuestsHelper;
import com.example.bugdroid.lolquests.GameAdapter;
import com.example.bugdroid.lolquests.GameHistory;
import com.example.bugdroid.lolquests.Objects.Global;
import com.example.bugdroid.lolquests.Objects.Quest;
import com.example.bugdroid.lolquests.Outros.AlarmReceiver;
import com.example.bugdroid.lolquests.Outros.ViewDialog;
import com.example.bugdroid.lolquests.R;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.game.Game;
import com.robrua.orianna.type.exception.APIException;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.bugdroid.lolquests.GameHistory.gameListAdapter;
import static com.example.bugdroid.lolquests.GameHistory.matchHistory;
import static com.robrua.orianna.api.core.StaticDataAPI.getItem;

public class ProfileQuest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
