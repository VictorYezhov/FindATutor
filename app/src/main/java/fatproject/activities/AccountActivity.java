package fatproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import fatproject.findatutor.R;

/**
 * Created by Victor on 09.01.2018.
 */

public class AccountActivity extends AppCompatActivity {
    //private DrawerLayout mDrawerLayout;
    //private ActionBarDrawerToggle mToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(mToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
