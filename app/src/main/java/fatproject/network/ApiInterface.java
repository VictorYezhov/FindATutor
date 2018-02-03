package fatproject.network;

/**
 * Created by Max Komarenski on 03.02.2018.
 */

import java.util.List;

import fatproject.model.Message;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("inbox.json")
    Call<List<Message>> getInbox();

}
