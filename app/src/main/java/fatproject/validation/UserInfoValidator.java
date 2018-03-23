package fatproject.validation;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import fatproject.findatutor.R;

/**
 * Created by Victor on 21.03.2018.
 */

public class UserInfoValidator implements Validator {


    @Override
    public boolean validateFragment(View fragment) {


        EditText editTextNumber =fragment.findViewById(R.id.editTextNumber);
        EditText editTextCity = fragment.findViewById(R.id.editTextCity);
        AutoCompleteTextView editTextCountry = fragment.findViewById(R.id.editTextCountry);

        //---
        //---
        //---
        TextInputLayout numberInputLayout = fragment.findViewById(R.id.numberInputLayout);
        TextInputLayout countryInputLayout = fragment.findViewById(R.id.countryInputLayout);
        TextInputLayout cityInputLayout = fragment.findViewById(R.id.cityInputLayout);

        if(editTextNumber.getText().toString().isEmpty()) {
            numberInputLayout.setError("Enter your number");
            return false;
        }
        if(editTextCountry.getText().toString().isEmpty()){
            countryInputLayout.setError("Enter Country");
            return false;
        }
        if(editTextCity.getText().toString().isEmpty()){
            cityInputLayout.setError("Enter city");
            return false;
        }
        return true;
    }

    @Override
    public boolean validateActivity(AppCompatActivity activity) {
        return false;
    }
}
