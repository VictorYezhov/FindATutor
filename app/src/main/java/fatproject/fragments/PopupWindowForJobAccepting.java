package fatproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.willy.ratingbar.ScaleRatingBar;

import fatproject.SendingForms.IdsForAppointment;
import fatproject.activities.MainAplication;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupWindowForJobAccepting extends AppCompatDialogFragment {
    private TextView userNameAndSureName;
    private BootstrapButton accessButton;
    private BootstrapButton cancelButton;
    private ScaleRatingBar rb;
    private String name;
    private String surename;
    private Long id_person_who_leave_comment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_window_for_job_accepting, null);
        view.setBackgroundColor(Color.WHITE);

        builder.setView(view);

        userNameAndSureName = view.findViewById(R.id.nameAndSureNameInPopupWindow);

        String nameAndSureName = name + " " + surename;
        userNameAndSureName.setText(nameAndSureName);

        accessButton = view.findViewById(R.id.accessButtonInDialogWindow);
        cancelButton = view.findViewById(R.id.cancelButtonInDialogWindow);
        //rb = view.findViewById(R.id.RatingBarInDialogWindow);

        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainAplication.getServerRequests().sendIdForNewAppointment(new IdsForAppointment( id_person_who_leave_comment, MainAplication.getCurrentUser().getId())).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.err.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        return builder.create();
    }

    public void setNameAndFamilyName(String s1, String s2, Long id){
        name = s1;
        surename = s2;
        id_person_who_leave_comment = id;
    }

}
