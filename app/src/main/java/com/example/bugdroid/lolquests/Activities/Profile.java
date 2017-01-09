package com.example.bugdroid.lolquests.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import com.example.bugdroid.lolquests.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Profile</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
