package fatproject.activities;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.view.MenuItem;

import java.util.Locale;

import io.paperdb.Paper;

/**
 * Created by Victor on 25.01.2018.
 */

public class MainAplication extends Application {


    private static MainAplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        System.err.println("!!!!!!!!!!!FIRST!!!!!!!!");
    }
    public MainAplication(){
        instance = this;
    }

    public static void translate(){


        String lang = Paper.book().read("language");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getContext().getResources().updateConfiguration(config,instance.getApplicationContext().getResources().getDisplayMetrics());



    }

    public static Context getContext() {
        return instance;
    }




}
