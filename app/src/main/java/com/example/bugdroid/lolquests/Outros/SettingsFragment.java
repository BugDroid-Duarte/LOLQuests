package com.example.bugdroid.lolquests.Outros;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.bugdroid.lolquests.R;

/**
 * Created by BugDroid on 27/12/2016.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
    }
}
