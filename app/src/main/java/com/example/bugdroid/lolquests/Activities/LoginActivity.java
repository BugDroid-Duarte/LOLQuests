package com.example.bugdroid.lolquests.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;

import com.example.bugdroid.lolquests.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PreferenceManager.setDefaultValues(this, R.xml.user_settings, false);

    }

    public void skip (View v)
    {
        Intent intent = new Intent(LoginActivity.this, InitialActivity.class);
        startActivity(intent);
    }
}
