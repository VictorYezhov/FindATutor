package fatproject.activities;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import fatproject.entity.User;
import fatproject.findatutor.R;
import fatproject.fragments.AboutUs;
import fatproject.fragments.Account;
import fatproject.fragments.AnswerQuestions;
import fatproject.fragments.AskQuestions;
import fatproject.fragments.Contacts;
import fatproject.fragments.Settings;
import io.paperdb.Paper;

/**
 * Created by Victor on 09.01.2018.
 * Class which is holder for all fragments
 * Performs fragment changing if needed
 */

public class FragmentDispatcher extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static FragmentManager fragmentManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager =getSupportFragmentManager();
        setContentView(R.layout.fragments_manager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mToggle.syncState();
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
           System.out.println("EXEPTION");
        }
        setUpNavigationDrawer(navigationView);

        try {

            fragmentManager.beginTransaction().replace(R.id.frame, Account.class.newInstance()).commit();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * When option from a side selected this function called
     */
    private void optionSelect(MenuItem item){

        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()){

            case R.id.settings: {
                fragmentClass = Settings.class;
                break;
            }
            case R.id.aboutUs: {
                fragmentClass = AboutUs.class;
                break;
            }
            case R.id.askQuestion: {
                fragmentClass = AskQuestions.class;
                break;
            }
            case R.id.answerQuestion:{
                fragmentClass = AnswerQuestions.class;
                break;
            }
            case R.id.contacts:{
                fragmentClass = Contacts.class;
                break;
            }
            case R.id.acount:{
                fragmentClass = Account.class;
                break;
            }
            case R.id.logout:{
                logout();
                return;
            }
            default:
                fragmentClass = AboutUs.class;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
        item.setCheckable(true);
        setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();
    }

    private void setUpNavigationDrawer(NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                optionSelect(item);
                return true;
            }
        });



    }

    /**
     * static function that launches fragments
     * @param fragmentClass - class of fragment which need to be launched
     * @return
     */
    public static boolean launchFragment(Class fragmentClass){

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

        return true;
    }

    /**
     * Logout user from app, and redirect him to LoginActivity
     * Performs delete  user from Paper database
     */
    private void logout(){
        MainAplication.deleteCurrentUser();
        MainAplication.deleteUsersPhoto();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        launchFragment(Account.class);
    }


}

