package fatproject.internet;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import fatproject.IncomingForms.CommentForm;
import fatproject.IncomingForms.QuestionForm;
import fatproject.IncomingForms.QuestionTopicAndPrice;
import fatproject.IncomingForms.ReviewsAndRating;
import fatproject.SendingForms.IdsForAppointment;
import fatproject.SendingForms.LoginForm;
import fatproject.SendingForms.SendSkillsForm;
import fatproject.SendingForms.UserInformationForm;
import fatproject.entity.Appointment;
import fatproject.entity.Category;
import fatproject.entity.Contact;
import fatproject.entity.Country;
import fatproject.entity.Job;
import fatproject.entity.Message;
import fatproject.entity.Question;
import fatproject.entity.Skill;
import fatproject.entity.User;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("/userInfo")
    Call<User> loadUserInfo(@Query("id") Long id);

    @POST("/createNewChat")
    Call<String> createNewChat(@Query("side1") Long side1, @Query("side2")Long side2);


    @GET("/getQuestionTitle{id}")
    Call<String> getQuestionTitle(@Path("id") Long id);



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
     * Method for changing user number and user city.
     * @param userInformationForm
     * @return
     */
    @POST("/sendUserInformation{id}")
    Call<String> sendUserInformation(@Body UserInformationForm userInformationForm, @Path("id") Long id);

    /**
     * Registration method
     * @param user
     * @return
     */
    @POST("/user/add")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> registerNewUser(@Body User user);

    /**
     * Downloads list of user contacts
     * @return
     */
    @POST("/getMyContacts")
    Call<List<Contact>> getMyContacts(@Query("id") Long id);

    /**
     * Register or login google user
     * @param user
     * @return
     */
    @POST("/googleLogin")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> sendGoogleUser(@Body User user);

    @POST("/updateAppointment")
    Call<String> updateAppointment(@Body Appointment appointment);



    @POST("/updatePhoto{id}")
    @Multipart
    Call<String> updateUserPhoto(@Part MultipartBody.Part img, @Path("id") Long id);

    @POST("/getMessages{id}")
    Call<List<Message>> loadMessages(@Path("id") Long id, @Query("userId") Long requester);

    @POST("/getAllMessages{id}")
    Call<List<Message>> loadAllMessages(@Path("id") Long id, @Query("userId") Long requester);

    @POST("/sendMessage")
    Call<String> sendMessage(@Body Message message);

    @GET("/getQuestionsByCategory{id}")
    Call<List<QuestionForm>> getQuestionsByCategory(@Path("id") Long id);



    @GET("/getAllCategories")
    Call<List<Category>> getAllCategories();



    /**
     * download changes
     * @param loginForm userame and password
     * @return updated user
     */
    @POST("/updateUser")
    Call<User> updateUser(@Body LoginForm loginForm);

    @POST("/sendNewJob{id}")
    Call<String> updateJobs(@Body Job job, @Path("id") Long id);

    @POST("/sendAskingQuestion{id}")
    Call<Long> sendAskingQuestion(@Body Question question, @Path("id") Long id);

    @GET("/getCommentsOfCurrentQuestion{id}")
    Call<List<CommentForm>> getListOfCommentForms(@Path("id") Long id);

    @POST("/sendNewComment{id}")
    Call<String> sendNewComment(@Body fatproject.entity.Comment comment, @Path("id") Long id);

    @POST("/updateFCMIdToken")
    Call<String> updateIdToken(@Query("user_id") Long userId, @Query("token") String token);

    /**
     * Get list of jobs
     */
    @GET("/jobs")
    Call<Set<Job>> getJobs(@Query("id") String id);

    /**
     * Loads user image from server
     * @param id  user id
     * @return ResponseBody with byte[]
     */
    @GET("/getImage{id}")
    Call<ResponseBody> getUserImage(@Path("id") Long id);

    /**
     * Get list of all available questions
     * @return
     */
    @GET("/getAllQuestions")
    Call<List<QuestionForm>> getAllQuestions();

    @GET("/getAllCountries")
    Call<List<Country>> getAllCountries();

    @POST("/deleteItemFromJobList{id}")
    Call<String> deleteItemFromJobList(@Body Long id_user, @Path("id") Long id);

    @POST("/deleteItemFromChipList{id}")
    Call<String> deleteItemFromAccountChipList(@Body Long id_user, @Path("id") Long id);

    @GET("/getAllUsersQuestions{id}")
    Call<List<Question>> getAllUsersQuestions(@Path("id") Long id);

    @POST("/plusOneToViewCounter{id}")
    Call<String> plusOne(@Path("id") Long id);

    @GET("/getReviewsAndRating{id}")
    Call<ReviewsAndRating> getReviewsAndRating(@Path("id") Long id);

    @POST("/newAppointment")
    Call<String> sendIdForNewAppointment(@Body IdsForAppointment ifa);

    @GET("/nameOfPersonWhoLeftComment{id}")
    Call<String> getNameOfPersonWhoLeftComment(@Path("id") Long id);

    @GET("/getAllUserAppointments{id}")
    Call<List<Appointment>> getAllUserAppointments(@Path("id") Long id);

    @GET("/getTopicAndPriceOfQuestion{id}")
    Call<QuestionTopicAndPrice> getTopicAndPriceOfQuestion(@Path("id") Long id);

    @GET("/getNameOfYourPartner{id}")
    Call<String> getNameOfYourPartner(@Path("id") Long id);

    @POST("/changeAcceptingOnServerSide")
    Call<String> changeAcceptingOnServerSide(@Query("contract_id") Long contract_id,
                                             @Query("person_id") Long person_id,
                                             @Query("accepting") boolean accepting,
                                             @Query("another_person_id") Long another_person_id);

    @POST("/changeVariableOfContractEnd")
    Call<String> sayServerThatMeetingIsOver(@Query("contract_id") Long contract_id,
                                            @Query("person_id") Long person_id,
                                            @Query("end") boolean end,
                                            @Query("another_person_id") Long another_person_id);

    @POST("/sendReviewAndRating")
    Call<String> sendReviewAndRating(@Query("user_about") Long id_about,
                                     @Query("rating") float rating,
                                     @Query("review") String reviewText,
                                     @Query("user_from") Long id_from,
                                     @Query("contract_id") Long id);

    @GET("/updateAppointment{id}")
    Call<Appointment> updateAppointmentById(@Path("id") Long id);

    @POST("/checkIfTwoUsersAreContacted")
    Call<Boolean> checkIfTwoUsersAreContacted(@Query("personWhoAskedQuestion") Long personWhoAskedQuestion,
                                              @Query("personWhoLeftComment") Long personWhoLeftComment);

    @POST("/deleteAppointment{id}")
    Call<String> deleteAppointment(@Path("id") Long id);


    @POST("/startAppointment")
    Call<String> startAppointment(@Query("id") Long id);
}
