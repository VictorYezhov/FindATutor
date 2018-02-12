package fatproject.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fatproject.activities.MainAplication;
import fatproject.entity.Message;
import fatproject.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 03.02.2018.
 */

public class ServerConnector {


    private static RequestResult result;


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


    public static RequestResult singUpNewUser(User user){

        MainAplication.getServerRequests().registerNewUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null) {
                    result = RequestResult.RESULT_OK;
                    System.err.println(result.name());
                }
                else {
                    result = RequestResult.RESULT_NULL_ANSWER;

                    System.err.println(result.name());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result = RequestResult.RESULT_ERROR;

                System.err.println(result.name());

            }
        });

        return result;
    }


}
