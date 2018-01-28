package fatproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import fatproject.findatutor.R;

/**
 * Created by Victor on 26.01.2018.
 */

public class ProcessingActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        final ProgressDialog progressDialog1 = new ProgressDialog(ProcessingActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog1.setMessage("Applying Changes...");
        progressDialog1.show();

       //TODO якимось чудом зробити локалізацію


        progressDialog1.dismiss();


        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);



    }


}
