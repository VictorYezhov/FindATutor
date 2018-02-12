package fatproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.entity.User;
import fatproject.findatutor.R;
import fatproject.internet.RequestResult;
import fatproject.internet.ServerConnector;
import fatproject.validation.SingUpValidator;
import fatproject.validation.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 30.12.2017.
 */

public class SingUpActivity  extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_surname)
    EditText _surnamenameText;
    @BindView(R.id.input_address)
    EditText _addressText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                onPause();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public boolean signUp() {
        Log.d(TAG, "SignUp");
        final ProgressDialog progressDialog = new ProgressDialog(SingUpActivity.this,
                R.style.AppTheme_Dark_Dialog);

        if(!ServerConnector.checkConnection()){
            Toast.makeText(getBaseContext(), "No internet connection", Toast.LENGTH_LONG).show();
            Log.d(TAG, "No connection");
            onStop();
            onRestart();

        }
        //TODO Dependency injection using Dagger 2
        Validator singUpValidator = new SingUpValidator();
        progressDialog.setMessage("Validating information...");
        progressDialog.show();

        if (!singUpValidator.validate(this)) {
            progressDialog.setMessage("Validating error...");
            progressDialog.show();
            onSignupFailed();
            onRestart();
            progressDialog.dismiss();
            return false;
        }

        _signupButton.setEnabled(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        User user = new User();
        user.setName(_nameText.getText().toString());
        user.setFamilyName(_surnamenameText.getText().toString());
        user.setEmail(_emailText.getText().toString());
        user.setPassword(_passwordText.getText().toString());
        user.setMobileNumber(_mobileText.getText().toString());
        user.setAddress(_addressText.getText().toString());
        user.setRating(0);

       ServerConnector.singUpNewUser(user);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);


        // TODO:  email валідація.


        return true;
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();
        onStop();
        _signupButton.setEnabled(true);
    }



    public EditText get_nameText() {
        return _nameText;
    }


    public EditText get_addressText() {
        return _addressText;
    }


    public EditText get_emailText() {
        return _emailText;
    }


    public EditText get_mobileText() {
        return _mobileText;
    }


    public EditText get_reEnterPasswordText() {
        return _reEnterPasswordText;
    }


    public EditText get_passwordText() {
        return _passwordText;
    }


    public EditText get_surnamenameText() {
        return _surnamenameText;
    }

}
