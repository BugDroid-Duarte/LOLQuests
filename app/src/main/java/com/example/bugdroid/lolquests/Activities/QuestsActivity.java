package com.example.bugdroid.lolquests.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.bugdroid.lolquests.BD.QuestsHelper;
import com.example.bugdroid.lolquests.GameHistory;
import com.example.bugdroid.lolquests.R;
import com.robrua.orianna.type.core.game.Game;

import java.util.ArrayList;
import java.util.List;

public class QuestsActivity extends AppCompatActivity {

    private static final String TAG = "TESTE";
    public static List<Game> gameList = new ArrayList<>();
    public static ListAdapter listAdapter;
    public static List<String> versionsList = new ArrayList<>();
    QuestsHelper db = new QuestsHelper(this);
    public TextView desc;
    public TextView status;
    public TextView exp;
    public EditText search;
    public Button button;

    String nick = "xGatts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QuestsActivity.this, GameHistory.class);
                intent.putExtra("nome", search.getText().toString());
                startActivity(intent);
            }
        });
    }
}
