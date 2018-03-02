package fatproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.Locale;

import fatproject.activities.MainAplication;
import fatproject.activities.ProcessingActivity;
import fatproject.findatutor.R;
import io.paperdb.Paper;

/**
 * Created by Victor on 25.01.2018.
 */

public class Settings extends PreferenceFragmentCompat {


    private static final String TAG = "Settings";
    private ListPreference listPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.fragment_settings);

        listOfPreferensesInit();
    }


    private void listOfPreferensesInit() {
        listPreference = (ListPreference) findPreference("lang");

        listPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        String newLang = (String) newValue;
                        Paper.book().write("language", newLang);
                        Locale locale = new Locale(newLang);
                        Locale.setDefault(locale);
                        MainAplication.translate();
                        return true;
                    }


                });
                return true;
            }
        });


    }


}