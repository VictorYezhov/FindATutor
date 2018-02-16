package fatproject.internet;

import java.util.List;
import java.util.Set;

import fatproject.entity.LoginForm;
import fatproject.entity.Message;
import fatproject.entity.Skill;
import fatproject.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Victor on 03.02.2018.
 */

public interface ServerRequests {




    @GET("/index")
    Call<String> index();

    @GET("/skills")
    Call<Set<Skill>> getSkills(@Query("id") String id);


    @POST("/login")
    Call<User> login(@Body LoginForm loginForm);

    @POST("/user/add")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> registerNewUser(@Body User user);

    @POST("/simplepost")
    Call<String> simplePost();

    @GET("/getMessages")
    Call<List<Message>> getMessages();
}
