package fatproject.fragments;

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

import java.util.ArrayList;
import java.util.List;

import fatproject.IncomingForms.ReviewsAndRating;
import fatproject.SendingForms.IdsForAppointment;
import fatproject.activities.MainAplication;
import fatproject.adapter.ReviewsInDialogWindowAdapter;
import fatproject.entity.Review;
import fatproject.findatutor.R;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_window_for_job_accepting, null);
        view.setBackgroundColor(Color.WHITE);

        builder.setView(view);



        rar = new ReviewsAndRating();
        userNameAndSureName = view.findViewById(R.id.nameAndSureNameInPopupWindow);

        String nameAndSureName = name + " " + surename;
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

                onStop();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
            }
        });

        MainAplication.getServerRequests().getReviewsAndRating(id_person_who_leave_comment).enqueue(new Callback<ReviewsAndRating>() {
            @Override
            public void onResponse(Call<ReviewsAndRating> call, Response<ReviewsAndRating> response) {
                rar.setRating(response.body().getRating());
                rar.setReviews(response.body().getReviews());
                rb.setRating(rar.getRating());
            }

            @Override
            public void onFailure(Call<ReviewsAndRating> call, Throwable t) {

            }
        });

        return builder.create();
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
