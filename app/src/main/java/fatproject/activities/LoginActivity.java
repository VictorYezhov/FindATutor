package fatproject.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import fatproject.findatutor.R;
import fatproject.validation.LoginValidator;
import fatproject.validation.Validator;

/**
 * Created by Victor on 31.12.2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;


    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    @Bind(R.id.google_login)
    SignInButton SingIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setListener(_loginButton);
        setListener(_signupLink);

        SingIn.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        System.err.println("GOOGLE sign in options "+signInOptions.isIdTokenRequested());
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        googleApiClient.connect();

        System.err.println("GOOGLE api client is connected "+googleApiClient.isConnected());
        //--------------------------
    }


    public boolean login() {
        Log.d(TAG, "Login");
        Validator loginValidator = new LoginValidator();

        final ProgressDialog progressDialog1 = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog1.setMessage("Validating information...");
        progressDialog1.show();

        if (!loginValidator.validate(this)) {
            progressDialog1.setMessage("Validating error...");
            progressDialog1.show();
            onLoginFailed();
            onRestart();
            progressDialog1.dismiss();
            return false;
        }
        progressDialog1.dismiss();
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleResult(result);
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
        //----------------------------
        if(requestCode==REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }else {
            System.err.println("Something went wrong");
        }
        //----------------------------
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        onStop();
        _loginButton.setEnabled(true);
    }



    //-------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_login:
                singIn();
                break;
        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.err.println("CONNECTION FAILED");
    }

    private void singIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }

    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String given_name = account.getDisplayName();
            String given_email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            System.err.println(given_name+"  "+given_email+"\n "+img_url);
            updateUI(true);
        }else{
            System.err.println(result.getStatus().getStatusMessage());
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin){
        if(isLogin){
            SingIn.setVisibility(View.GONE);
        }else{
            //profSection.setVisibility(View.GONE);
            SingIn.setVisibility(View.VISIBLE);
        }
    }

    public EditText get_emailText() {
        return _emailText;
    }

    public EditText get_passwordText() {
        return _passwordText;
    }

    private void setListener(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void setListener(TextView txt){
        txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SingUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    //-------------------
}
