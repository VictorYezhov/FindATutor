package fatproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.SendingForms.UserInformationForm;
import fatproject.activities.MainAplication;
import fatproject.entity.Job;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Max Komarenski on 02.03.2018.
 */

public class SetUserInformation extends Fragment {
    @BindView(R.id.addNumberOrCityButton)
    Button addNumberOrCityButton;

    @BindView(R.id.numberInputLayout)
    TextInputLayout numberInputLayout;

    @BindView(R.id.cityInputLayout)
    TextInputLayout cityInputLayout;

    @BindView(R.id.editTextNumber)
    EditText editTextNumber;

    @BindView(R.id.editTextCity)
    EditText editTextCity;

    private Account.OnFragmentInteractionListener mListener;

    private static final String EMPTY_STRING = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_information, container, false);
        ButterKnife.bind(this, view);

        addNumberOrCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNumber.getText().toString().equals(EMPTY_STRING) && editTextCity.getText().toString().equals(EMPTY_STRING)) {
                    numberInputLayout.setError("Enter your number");
                    cityInputLayout.setError("Enter your local city");
                } else {
                    //send number and city
                    sendInformation(new UserInformationForm(editTextNumber.getText().toString(), editTextCity.getText().toString()));
                    editTextNumber.setText(EMPTY_STRING);
                    editTextCity.setText(EMPTY_STRING);

                    Toast.makeText(getActivity().getApplicationContext(), "Changes are saved", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    void sendInformation(UserInformationForm userInformationForm){
        MainAplication.getServerRequests().sendUserInformation(userInformationForm, MainAplication.getCurrentUser().getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
