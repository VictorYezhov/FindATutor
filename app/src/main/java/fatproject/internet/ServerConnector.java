package fatproject.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import fatproject.activities.MainAplication;

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
