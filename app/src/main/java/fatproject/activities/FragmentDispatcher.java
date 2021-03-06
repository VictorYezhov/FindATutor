package fatproject.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBufferObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.Helpers.ContractsQueue;
import fatproject.Helpers.ContractsQueueObserver;
import fatproject.Helpers.ImageSaver;
import fatproject.Helpers.MessageUpdateQueue;
import fatproject.entity.User;
import fatproject.findatutor.R;
import fatproject.fragments.AboutUs;
import fatproject.fragments.Account;
import fatproject.fragments.AnswerQuestions;
import fatproject.fragments.AskQuestions;
import fatproject.fragments.Contacts;
import fatproject.fragments.Contracts;
import fatproject.fragments.Settings;
import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 09.01.2018.
 * Class which is holder for all fragments
 * Performs fragment changing if needed
 */

public class FragmentDispatcher extends AppCompatActivity  implements ContractsQueueObserver, DataBufferObserver{

    private static FragmentDispatcher instance;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static FragmentManager fragmentManager;
    private static android.app.FragmentManager normalManager;
    private TextView myContacts;
    private MessageUpdateQueue updateQueue = MessageUpdateQueue.getInstance();
    private ContractsQueue contractsQueue = ContractsQueue.getInstance();

    NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        MainAplication.translate();
        fragmentManager = getSupportFragmentManager();

        normalManager = getFragmentManager();


        setContentView(R.layout.fragments_manager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        updateQueue.addObserver(this);
        contractsQueue.addObserver(this);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View header = navigationView.getHeaderView(0);

        TextView nameText = header.findViewById(R.id.nameNavigator);
        nameText.setText(MainAplication.getCurrentUser().getName());

        TextView sureText = header.findViewById(R.id.surenameNavigator);
        sureText.setText(MainAplication.getCurrentUser().getFamilyName());

        ImageView photo = header.findViewById(R.id.photoNavigator);
        loadPhotoFromServer(photo);

        myContacts = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.contacts));

        showNotification(updateQueue.getSize(), navigationView, R.id.contacts);
        showNotification(contractsQueue.getSize(), navigationView, R.id.contracts);


        mToggle.syncState();
        setTitle("Find a Tutor");
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

    private void showNotification(int count, NavigationView navigationView, int where){
        TextView view = (TextView) navigationView.getMenu().findItem(where).getActionView();
        if(count>0){

            view.setBackgroundColor(MainAplication.getContext().getResources().getColor(R.color.red));
            view.setText(String.valueOf(count));
        }
        else {
            view.setBackgroundColor(MainAplication.getContext().getResources().getColor(R.color.white));
        }
    }


    public static FragmentManager getFragmentManaget(){
        return fragmentManager;
    }

    public static android.app.FragmentManager getNormalManager(){
        return normalManager;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void loadPhotoFromServer(final ImageView v){
        MainAplication.getServerRequests().getUserImage(MainAplication.getCurrentUser().getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte byteForm[] = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteForm, 0, byteForm.length);
                    v.setImageBitmap(bitmap);
                    ImageSaver.saveToInternalStorage(bitmap);
                }catch (Exception e) {
                    e.printStackTrace();
                    v.setImageResource(R.drawable.noavatar);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public static Context getContext() {
        return instance;
    }
    /**
     * When option from a side selected this function called
     */
    private void optionSelect(MenuItem item){

        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()){

            case R.id.settings: {
                //fragmentClass = Settings.class;
                finish();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return;
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
            case R.id.contracts: {
                fragmentClass = Contracts.class;
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
        for(String s: Paper.book().getAllKeys()){
            Paper.book().delete(s);
        }
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        launchFragment(Account.class);
    }

    @Override
    public void onDataChanged() {
        showNotification(updateQueue.getSize(), navigationView, R.id.contacts);
    }
    @Override
    public void updateContracts() {
        showNotification(contractsQueue.getSize(), navigationView, R.id.contracts);
    }

    @Override
    public void dataChanged() {
        showNotification(contractsQueue.getSize(), navigationView, R.id.contracts);
    }

    @Override
    public void onDataRangeChanged(int i, int i1) {

    }

    @Override
    public void onDataRangeInserted(int i, int i1) {

    }

    @Override
    public void onDataRangeRemoved(int i, int i1) {

    }

    @Override
    public void onDataRangeMoved(int i, int i1, int i2) {

    }


}

