package fatproject.internet;

import java.io.File;
import java.util.List;
import java.util.Set;

import fatproject.SendingForms.LoginForm;
import fatproject.SendingForms.SendSkillsForm;
import fatproject.entity.Message;
import fatproject.entity.Skill;
import fatproject.entity.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Victor on 03.02.2018.
 */

public interface ServerRequests {


    /**
     * Method returns users skills whith specified id
     * @param id - user id
     * @return set of skills
     */
    @GET("/skills")
    Call<Set<Skill>> getSkills(@Query("id") String id);

    /**
     * Method returns all available skill names
     * @return
     */
    @GET("/getAllSkills")
    Call<List<Skill>> getAllAvailableSkills();


    /**
     * Used when user add`s to himself new skills
     * @param form - form which contains list of skills
     * @return state
     */
    @POST("/sendNewSkills")
    Call<String> sendNewSkills(@Body SendSkillsForm form);

    /**
     * Method for user login to server
     * @param loginForm
     * @return
     */
    @POST("/login")
    Call<User> login(@Body LoginForm loginForm);

    /**
     * Registration method
     * @param user
     * @return
     */
    @POST("/user/add")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> registerNewUser(@Body User user);


    /**
     * Downloads list of usser messages
     * @return
     */
    @GET("/getMessages")
    Call<List<Message>> getMessages();

    /**
     * Register or login google user
     * @param user
     * @return
     */
    @POST("/googleLogin")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> sendGoogleUser(@Body User user);


    @POST("/updatePhoto{id}")
    @Multipart
    Call<String> updateUserPhoto(@Part MultipartBody.Part img, @Path("id") Long id);



    /**
     * download changes
     * @param loginForm userame and password
     * @return updated user
     */
    @POST("/updateUser")
    Call<User> updateUser(@Body LoginForm loginForm);
}
