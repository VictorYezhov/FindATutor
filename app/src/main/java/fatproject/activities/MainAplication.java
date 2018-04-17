package fatproject.activities;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

import fatproject.Helpers.StrConstansts;
import fatproject.entity.User;
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

        Paper.init(this);


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URLs.BASE_LOCKAL.getUrl()) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        serverRequests = retrofit.create(ServerRequests.class);


        translate();

        System.err.println("!!!!!!!!!!!FIRST!!!!!!!!  ");
    }
    public MainAplication(){
        instance = this;
    }

    public static void translate(){


        String lang = Paper.book().read("language");

        if(lang==null){
            lang="en";
        }
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


    public static User getCurrentUser(){
        return Paper.book().read(StrConstansts.CURRENTUSER.getParameter());
    }
    public static void saveCurrentUser(User user){
        Paper.book().write(StrConstansts.CURRENTUSER.getParameter(),user);
    }
    public static void deleteCurrentUser(){
        Paper.book().delete(StrConstansts.CURRENTUSER.getParameter());
    }
    public static String getUsersPhoto(){
        return Paper.book().read(StrConstansts.PATHTOPHOTO.getParameter());
    }
    public static void  savePathToPhoto(String path){
        Paper.book().write(StrConstansts.PATHTOPHOTO.getParameter(),path);
    }
    public static void deleteUsersPhoto(){
        Paper.book().delete(StrConstansts.PATHTOPHOTO.getParameter());
    }
}
