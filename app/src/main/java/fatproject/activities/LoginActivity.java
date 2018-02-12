package fatproject.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.entity.LoginForm;
import fatproject.entity.User;
import fatproject.findatutor.R;
import fatproject.validation.LoginValidator;
import fatproject.validation.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 31.12.2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int RC_SIGN_IN = 2;

    private  GoogleSignInClient googleSignInClient;


    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    @BindView(R.id.google_login)
    SignInButton SingIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        _loginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
        SingIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        //--------------------------
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            System.err.println("onStart method");
        }
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
        onPause();
        Intent intent = new Intent(getApplicationContext(), FragmentDispatcher.class);
        startActivityForResult(intent, REQUEST_SIGNUP);

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        MainAplication.getServerRequests().login(new LoginForm(email, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println(response.body());
                if(response.body() != null){
                    System.out.println(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

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
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            System.err.println(account.getEmail() + " " + account.getDisplayName() + account.getFamilyName() + account.getGivenName());
//            Paper.book().write("email", account.getEmail());
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            System.err.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
        }
    }



    //-------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_login:
                signIn();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.link_signup:{
                Intent intent = new Intent(getApplicationContext(), SingUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                break;
            }
        }
    }




    public EditText get_emailText() {
        return _emailText;
    }

    public EditText get_passwordText() {
        return _passwordText;
    }



    //-------------------
}
