package fatproject.Helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Victor on 25.01.2018.
 */

public class LocaleManager {
    private static final String SELECTED_LANG = "Locale.Manager.Selected.Lang";

    public static Context onAtach(Context context){
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());

        return setLocale(context, lang);
    }
    public static Context onAtach(Context context, String defaulLang){
        String lang = getPersistedData(context, defaulLang);

        return setLocale(context, lang);
    }

    private static Context setLocale(Context context, String lang) {
        persist(context, lang);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return  updateResourses(context, lang);
        }
        return updateResoursesLegasy(context,lang);
}

@TargetApi(Build.VERSION_CODES.N)
    private static Context updateResourses(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

    Configuration configuration   = context.getResources().getConfiguration();
    configuration.setLocale(locale);
    configuration.setLayoutDirection(locale);

    return context.createConfigurationContext(configuration);

    }
    @SuppressWarnings("deprecation")
    private static Context updateResoursesLegasy(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        return context;

    }

    private static void persist(Context context, String lang){
         SharedPreferences  preferences = PreferenceManager.getDefaultSharedPreferences(context);
         SharedPreferences.Editor editor = preferences.edit();
         editor.putString(SELECTED_LANG,lang);
         editor.apply();

}

    private static String getPersistedData(Context context, String language) {

        SharedPreferences  preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANG,language);
    }
}
