package fatproject.internet;

import java.util.List;

import fatproject.entity.Greeting;
import fatproject.entity.Message;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Victor on 03.02.2018.
 */

public interface ServerRequests {



    @GET("/home")
    Call<Greeting> requestGreeting();
    @GET("/index")
    Call<String> index();

    @GET("/getMessages")
    Call<List<Message>> getMessages();
}
