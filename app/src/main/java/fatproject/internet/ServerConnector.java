package fatproject.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fatproject.activities.MainAplication;
import fatproject.entity.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 03.02.2018.
 */

public class ServerConnector {


        public static boolean checkConnection(){
        ConnectivityManager connectivityManager =(ConnectivityManager)MainAplication.getContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo.isConnected();
        }
        catch (NullPointerException e ){
            e.printStackTrace();
            return false;
        }
    }

    public static  boolean getMessages(final List<Message> input){


            MainAplication.getServerRequests().getMessages().enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                    if(response.body()!=null){
                        input.addAll(response.body());

                    }
                    else {
                        System.err.println("NULL RESPONSE BODY");
                    }

                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {

                    t.printStackTrace();
                }
            });

        return true;

    }



//    App.getApi().getData("bash", 50).enqueue(new Callback<List<PostModel>>() {
//        @Override
//        public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
//            //Данные успешно пришли, но надо проверить response.body() на null
//        }
//        @Override
//        public void onFailure(Call<List<PostModel>> call, Throwable t) {
//            //Произошла ошибка
//        }
//    });



}
