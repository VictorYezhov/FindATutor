package fatproject.dialogWindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.willy.ratingbar.ScaleRatingBar;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fatproject.IncomingForms.ReviewsAndRating;
import fatproject.SendingForms.IdsForAppointment;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.adapter.ReviewsInDialogWindowAdapter;
import fatproject.entity.Message;
import fatproject.entity.Review;
import fatproject.findatutor.R;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupWindowForJobAccepting extends AppCompatDialogFragment implements View.OnClickListener{
    private TextView userNameAndSureName;
    private BootstrapButton accessButton;
    private BootstrapButton cancelButton;
    private ScaleRatingBar rb;
    private String name;
    private String surename;
    private Long id_person_who_leave_comment;
    private Long id_of_question;
    private ReviewsAndRating rar;
    private ExpandableLayout expandableLayout;
    private ReviewsInDialogWindowAdapter reviewsInDialogWindowAdapter;
    private List<Review> reviews = new ArrayList<>();
    private RecyclerView reviewRecyclerView;
    private int cheker = 0;
    String nameAndSureName;
    private PopupWindowForJobAccepting instance = this;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_window_for_job_accepting, null);
        view.setBackgroundColor(Color.WHITE);

        builder.setView(view);

        rar = new ReviewsAndRating();
        userNameAndSureName = view.findViewById(R.id.nameAndSureNameInPopupWindow);

        nameAndSureName = name + " " + surename;
        userNameAndSureName.setText(nameAndSureName);

        accessButton = view.findViewById(R.id.accessButtonInDialogWindow);
        cancelButton = view.findViewById(R.id.cancelButtonInDialogWindow);
        rb = view.findViewById(R.id.RatingBarInDialogWindow);

        expandableLayout = view.findViewById(R.id.expandable_layout_in_dialog_window);
        reviewRecyclerView = view.findViewById(R.id.reviewRecyclerViewInDialogWindow);

        //----------------ReviewRecyclerView-----------------
        reviewsInDialogWindowAdapter = new ReviewsInDialogWindowAdapter(reviews);
        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this.getContext());

        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewRecyclerView.setAdapter(reviewsInDialogWindowAdapter);
        reviewsInDialogWindowAdapter.notifyDataSetChanged();


        expandableLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {

            }
        });

        view.findViewById(R.id.expand_button_in_dialog_window).setOnClickListener(this);


        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainAplication.getServerRequests().sendIdForNewAppointment(new IdsForAppointment( id_person_who_leave_comment, MainAplication.getCurrentUser().getId(), id_of_question)).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.err.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                MainAplication.getServerRequests().
                        createNewChat(id_person_who_leave_comment, MainAplication.getCurrentUser().getId())
                        .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body()!=null){
                            sendWelcomeMessage(Long.decode(response.body()));
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                System.err.println(nameAndSureName);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.onDestroy();
                instance.dismiss();
            }
        });

        MainAplication.getServerRequests().getReviewsAndRating(id_person_who_leave_comment).enqueue(new Callback<ReviewsAndRating>() {
            @Override
            public void onResponse(Call<ReviewsAndRating> call, Response<ReviewsAndRating> response) {
                rar.setRating(response.body().getRating());
                rar.setReviews(response.body().getReviews());
                rb.setRating(rar.getRating());
                reviews.clear();
                reviews.addAll(rar.getReviews());
                reviewsInDialogWindowAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ReviewsAndRating> call, Throwable t) {

            }
        });



        return builder.create();
    }


    public void sendWelcomeMessage(Long contactId){

        MainAplication.getServerRequests().getQuestionTitle(id_of_question).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Message message = new Message();
                String textMessage = getString(R.string.welcome_messages, name+surename, response.body().replaceAll("@", " "));
                message.setContactId(contactId);
                message.setFrom(MainAplication.getCurrentUser().getId());
                message.setColor(0);
                message.setRead(false);
                message.setMessage(textMessage);
                message.setTimestamp(new Timestamp(System.currentTimeMillis()));
                List<Message> cashedMessages = Paper.book().read("messages"+contactId);
                if(cashedMessages!=null) {
                    cashedMessages.add(message);
                    Paper.book().write("messages" + contactId, cashedMessages);
                }else {
                    cashedMessages = new ArrayList<>();
                    cashedMessages.add(message);
                    Paper.book().write("messages" + contactId, cashedMessages);
                }
                MainAplication.getServerRequests().sendMessage(message).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body()!=null) {
                            Log.d("Message :", response.body());
                        }
                        instance.onDestroy();
                        instance.dismiss();
                        EasyPopupWindow easyPopupWindow = new EasyPopupWindow();
                        easyPopupWindow.initialiseMessage("Dear, %3$s you chose %4$s as a tutor for your question. \n" +
                                "we send him a automatic mail.   You can find it in your contacts list, and in arrangements section", MainAplication.getCurrentUser().getName(),nameAndSureName);
                        easyPopupWindow.show(FragmentDispatcher.getFragmentManaget(), "popup");
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Message :", "sending fail");
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    public void setNameAndFamilyName(String s1, String s2, Long id, Long question_id){
        name = s1;
        surename = s2;
        id_person_who_leave_comment = id;
        id_of_question = question_id;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.expand_button_in_dialog_window && cheker == 0) {
            expandableLayout.expand();
            cheker = 1;
        } else {
            expandableLayout.collapse();
            cheker = 0;
        }


    }
}
