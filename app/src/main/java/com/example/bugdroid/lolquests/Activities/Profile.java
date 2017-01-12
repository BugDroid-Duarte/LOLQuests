package com.example.bugdroid.lolquests.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bugdroid.lolquests.R;

import org.w3c.dom.Text;

import static android.R.attr.id;

public class Profile extends AppCompatActivity {

    public TextView realname;
    public TextView email;
    public TextView member;
    public TextView points;
    public TextView ign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_profile);

        realname = (TextView) findViewById(R.id.realname);
        email = (TextView) findViewById(R.id.email);
        member = (TextView) findViewById(R.id.datamember);
        points = (TextView) findViewById(R.id.points);
        ign = (TextView) findViewById(R.id.ign);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Profile</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // obtem valores do utilizador que foram guardados no sharedpreferences ao fazer login
        realname.setText(sharedPref.getString("name", ""));
        email.setText(sharedPref.getString("email", ""));
        member.setText("Since: " + sharedPref.getString("member", ""));
        points.setText(String.valueOf(sharedPref.getInt("points", 0)));
        ign.setText(sharedPref.getString("username", ""));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
