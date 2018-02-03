package fatproject.internet;

import fatproject.entity.Greeting;
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
}
