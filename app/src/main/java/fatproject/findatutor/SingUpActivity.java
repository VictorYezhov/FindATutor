package fatproject.findatutor;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import fatproject.validation.SingUpValidator;
import fatproject.validation.Validator;

/**
 * Created by Victor on 30.12.2017.
 */

public class SingUpActivity  extends AppCompatActivity {


    private static final String TAG = "SignUpActivity";

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_surname)
    EditText _surnamenameText;
    @Bind(R.id.input_address)
    EditText _addressText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_mobile)
    EditText _mobileText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");
        //TODO Dependensiy injection using Dagger 2
        Validator singUpValidator = new SingUpValidator();

        if (singUpValidator.validate(this)) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SingUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        System.out.println(name+"\n"+address+"\n"+email+"\n"+mobile+"\n"+password+"\n"+reEnterPassword);
        // TODO: Відправка даних на сервер та збереження в БД + email валідація.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }





    public static String getTAG() {
        return TAG;
    }

    public EditText get_nameText() {
        return _nameText;
    }

    public void set_nameText(EditText _nameText) {
        this._nameText = _nameText;
    }

    public EditText get_addressText() {
        return _addressText;
    }

    public void set_addressText(EditText _addressText) {
        this._addressText = _addressText;
    }

    public EditText get_emailText() {
        return _emailText;
    }

    public void set_emailText(EditText _emailText) {
        this._emailText = _emailText;
    }

    public EditText get_mobileText() {
        return _mobileText;
    }

    public void set_mobileText(EditText _mobileText) {
        this._mobileText = _mobileText;
    }

    public EditText get_reEnterPasswordText() {
        return _reEnterPasswordText;
    }

    public void set_reEnterPasswordText(EditText _reEnterPasswordText) {
        this._reEnterPasswordText = _reEnterPasswordText;
    }

    public EditText get_passwordText() {
        return _passwordText;
    }

    public void set_passwordText(EditText _passwordText) {
        this._passwordText = _passwordText;
    }

    public Button get_signupButton() {
        return _signupButton;
    }

    public void set_signupButton(Button _signupButton) {
        this._signupButton = _signupButton;
    }

    public TextView get_loginLink() {
        return _loginLink;
    }

    public void set_loginLink(TextView _loginLink) {
        this._loginLink = _loginLink;
    }

    public EditText get_surnamenameText() {
        return _surnamenameText;
    }

    public void set_surnamenameText(EditText _surnamenameText) {
        this._surnamenameText = _surnamenameText;
    }
}
