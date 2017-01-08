package com.example.bugdroid.lolquests.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bugdroid.lolquests.BD.SQLiteHandler;
import com.example.bugdroid.lolquests.BD.SessionManager;
import com.example.bugdroid.lolquests.R;

import java.util.HashMap;

public class ProfileQuest extends Activity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
