package fatproject.validation;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fatproject.activities.LoginActivity;

/**
 * Created by Komarenski Max on 02.01.2018.
 */

public class LoginValidator implements Validator{
    public LoginValidator(){

    }

    @Override
    public boolean validateActivity(AppCompatActivity activity) {
        boolean valid = true;

        String email = ((LoginActivity) activity).get_emailText().getText().toString();
        String password = ((LoginActivity) activity).get_passwordText().getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ((LoginActivity) activity).get_emailText().setError("enter a valid email address");
            valid = false;
        } else {
            ((LoginActivity) activity).get_emailText().setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            ((LoginActivity) activity).get_passwordText().setError("between 4 and 16 alphanumeric characters");
            valid = false;
        } else {
            ((LoginActivity) activity).get_passwordText().setError(null);
        }

        return valid;
    }

    @Override
    public boolean validateFragment(View fragment) {
        return false;
    }
}
