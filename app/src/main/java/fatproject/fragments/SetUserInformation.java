package fatproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.SendingForms.UserInformationForm;
import fatproject.activities.MainAplication;
import fatproject.entity.Country;
import fatproject.entity.Job;
import fatproject.findatutor.R;
import fatproject.validation.UserInfoValidator;
import fatproject.validation.Validator;
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

    @BindView(R.id.countryInputLayout)
    TextInputLayout countryInputLayout;

    @BindView(R.id.cityInputLayout)
    TextInputLayout cityInputLayout;

    @BindView(R.id.editTextCity)
    EditText editTextCity;

    @BindView(R.id.editTextNumber)
    EditText editTextNumber;

    @BindView(R.id.editTextCountry)
    AutoCompleteTextView editTextCountry;

    private Validator validator;

    private List<String> countries;


    private Account.OnFragmentInteractionListener mListener;

    private static final String EMPTY_STRING = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_change_user_information, container, false);
        ButterKnife.bind(this, view);
        countries = new ArrayList<>();
        validator = new UserInfoValidator();
        editTextNumber.setText(MainAplication.getCurrentUser().getMobileNumber());
        editTextCity.setText(MainAplication.getCurrentUser().getCity().getName());
        editTextCountry.setText(MainAplication.getCurrentUser().getCity().getCountry().getName());

        MainAplication.getServerRequests().getAllCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                for (Country c : response.body()) {
                    countries.add(c.getName());
                }
            }
            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });
        editTextCountry.setAdapter(new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, countries));

        addNumberOrCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validator.validateFragment(view)) {
                } else {
                    UserInformationForm  form = new UserInformationForm();
                    form.setNumber(editTextNumber.getText().toString());
                    form.setCity(editTextCity.getText().toString());
                    form.setCountry(editTextCountry.getText().toString());
                    sendInformation(form);
                    Toast.makeText(getActivity().getApplicationContext(), "Changes are saved", Toast.LENGTH_LONG).show();
                }

                hideKeyBoard(view);
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

    void hideKeyBoard(View v){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private  void setListenersToWidgets(){


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
