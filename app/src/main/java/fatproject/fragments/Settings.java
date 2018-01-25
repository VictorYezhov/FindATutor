package fatproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;

import java.util.Locale;

import fatproject.activities.AccountActivity;
import fatproject.activities.LoginActivity;
import fatproject.activities.MainAplication;
import fatproject.activities.ProcessingActivity;
import fatproject.findatutor.R;
import io.paperdb.Paper;

/**
 * Created by Victor on 25.01.2018.
 */

public class Settings extends PreferenceFragmentCompat {

    private ListPreference listPreference;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.fragment_settings);
        Paper.init(this.getContext());
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
                        startIntent();
                        return true;
                    }


                });
                return true;
            }
        });


    }

    private void startIntent() {
        Intent intent = new Intent(this.getContext(), ProcessingActivity.class);
        startActivity(intent);
    }


//    private void changeLang() {
//        String lang = Paper.book().read("language");
//        Locale myLocale = new Locale(lang);
//
//        System.err.println("LOCALE " + myLocale.getLanguage()+" Lang "+ lang);
//        Locale.setDefault(myLocale);
//        android.content.res.Configuration config = new android.content.res.Configuration();
//        config.locale = myLocale;
//
//
//        this.getContext().getResources().updateConfiguration(config, this.getContext().getResources().getDisplayMetrics());
//    }
}