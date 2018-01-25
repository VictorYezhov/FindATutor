package fatproject.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import fatproject.findatutor.R;

/**
 * Created by Victor on 25.01.2018.
 */

public class Settings extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        addPreferencesFromResource(R.xml.fragment_settings);
    }
}
