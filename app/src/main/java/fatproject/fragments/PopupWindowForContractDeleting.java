package fatproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupWindowForContractDeleting extends AppCompatDialogFragment implements View.OnClickListener{

    private Long id;

    private PopupWindowForContractDeleting instance;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.proved_window_of_deleting_contract, null);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.otf");
        TextView areYouSureWord = view.findViewById(R.id.areYouSureText);
        areYouSureWord.setTypeface(font);

        instance = this;
        BootstrapButton yes = view.findViewById(R.id.yesButtonInContractDeletingWindow);
        BootstrapButton cancel = view.findViewById(R.id.canselButtonInContractDeletingWindow);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAppointmentFromServer(id);
                FragmentDispatcher.launchFragment(Contracts.class);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.dismiss();
                instance.onStop();
                instance.onDestroy();

            }
        });

        builder.setView(view);


        return builder.create();
    }

    public void initialise(Long id){
        this.id = id;
    }


    @Override
    public void onClick(View v) {

    }

    public void deleteAppointmentFromServer(Long id){
        MainAplication.getServerRequests().deleteAppointment(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
                instance.dismiss();
                instance.onStop();
                instance.onDestroy();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
