package fatproject.activities;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.view.MenuItem;

import java.util.Locale;

import fatproject.internet.ServerConnector;
import fatproject.internet.ServerRequests;
import fatproject.internet.URLs;
import io.paperdb.Paper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Victor on 25.01.2018.
 */

public class MainAplication extends Application {


    private static MainAplication instance;
    private static ServerRequests  serverRequests;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(URLs.BASE.getUrl()) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        serverRequests = retrofit.create(ServerRequests.class);



        System.err.println("!!!!!!!!!!!FIRST!!!!!!!!  "+ServerConnector.checkConnection());
    }
    public MainAplication(){
        instance = this;
    }

    public static void translate(){


        String lang = Paper.book().read("language");
        System.err.println(Paper.book().getAllKeys());
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
    public static ServerRequests getServerRequests(){
        return serverRequests;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }


}
