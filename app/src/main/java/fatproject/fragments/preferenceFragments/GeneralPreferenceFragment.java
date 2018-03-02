package fatproject.fragments.preferenceFragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.preference.ListPreference;
import android.view.MenuItem;

import java.util.Locale;

import fatproject.activities.MainAplication;
import fatproject.activities.SettingsActivity;
import fatproject.findatutor.R;
import fatproject.fragments.Settings;
import io.paperdb.Paper;

/**
 * Created by Victor on 02.03.2018.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GeneralPreferenceFragment extends PreferenceFragment {

    private EditTextPreference editTextPreference;
    private Preference listPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(true);
        listPreference =  findPreference("lang");
        android.preference.ListPreference actualList = (android.preference.ListPreference) listPreference;

        int index = actualList.findIndexOfValue( actualList.getValue());
        listPreference.setSummary(
                index >= 0
                        ? actualList.getEntries()[index]
                        : null);
        editTextPreference = (EditTextPreference) findPreference("change_name");
        editTextPreference.setText(MainAplication.getCurrentUser().getName() + " " + MainAplication.getCurrentUser().getFamilyName());
        editTextPreference.setSummary(MainAplication.getCurrentUser().getName() + " " + MainAplication.getCurrentUser().getFamilyName());
        setListeners();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setListeners(){
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String  input  = o.toString();
                editTextPreference.setSummary(input);
                System.err.println("FRAGMET CHANGE LISTENER "+ input);
                return true;
            }
        });

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (preference instanceof android.preference.ListPreference) {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    android.preference.ListPreference listPreference = (android.preference.ListPreference) preference;
                    int index = listPreference.findIndexOfValue(o.toString());

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);
                    String newLang = (String) o;
                    Paper.book().write("language", newLang);
                    Locale locale = new Locale(newLang);
                    Locale.setDefault(locale);
                    MainAplication.translate();

                }
                return true;
            }
        });
    }


}
