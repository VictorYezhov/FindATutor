package fatproject.validation;

import android.support.v7.app.AppCompatActivity;

import fatproject.findatutor.SingUpActivity;

/**
 * Created by Victor on 31.12.2017.
 */

public class SingUpValidator implements Validator {


    public SingUpValidator() {
    }

    @Override
    public boolean validate(AppCompatActivity activity) {

        boolean valid = true;


        String name = ((SingUpActivity) activity).get_nameText().getText().toString();
        String surname = ((SingUpActivity) activity).get_surnamenameText().getText().toString();
        String address = ((SingUpActivity) activity).get_addressText().getText().toString();
        String email = ((SingUpActivity) activity).get_emailText().getText().toString();
        String mobile = ((SingUpActivity) activity).get_mobileText().getText().toString();
        String password = ((SingUpActivity) activity).get_passwordText().getText().toString();
        String reEnterPassword =((SingUpActivity) activity).get_reEnterPasswordText().getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            ((SingUpActivity) activity).get_nameText().setError("at least 3 characters");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_nameText().setError(null);
        }
        if (surname.isEmpty()) {
            ((SingUpActivity) activity).get_surnamenameText().setError("at least 3 characters");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_surnamenameText().setError(null);
        }

        if (address.isEmpty()) {
            ((SingUpActivity) activity).get_addressText().setError("Enter Valid Address");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_addressText().setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ((SingUpActivity) activity).get_emailText().setError("enter a valid email address");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_emailText().setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            ((SingUpActivity) activity).get_mobileText().setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_mobileText().setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            ((SingUpActivity) activity).get_passwordText().setError("between 4 and 16 alphanumeric characters");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_passwordText().setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 16 || !(reEnterPassword.equals(password))) {
            ((SingUpActivity) activity).get_reEnterPasswordText().setError("Password Do not match");
            valid = false;
        } else {
            ((SingUpActivity) activity).get_reEnterPasswordText().setError(null);
        }

        return valid;


    }
}
