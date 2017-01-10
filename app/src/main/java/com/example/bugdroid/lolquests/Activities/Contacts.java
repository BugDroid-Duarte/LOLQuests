package com.example.bugdroid.lolquests.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.example.bugdroid.lolquests.R;

public class Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Contact Us</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
