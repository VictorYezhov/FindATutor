package fatproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.SendingForms.LoginForm;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.adapter.ChipAdapter;
import fatproject.adapter.JobAdapter;
import fatproject.entity.Job;
import fatproject.entity.Skill;
import fatproject.entity.User;
import fatproject.findatutor.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Victor on 30.01.2018.
 */

public class Account extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.profile_image)
    ImageView profile_image;

    @BindView(R.id.chatButton)
    FloatingActionButton chatButton;

    @BindView(R.id.open_photo)
    FloatingActionButton open_photo;

    @BindView(R.id.delete_photo)
    FloatingActionButton delete_photo;

    @BindView(R.id.change_photo)
    FloatingActionButton change_photo;

    @BindView(R.id.plus_button)
    FloatingActionButton plus_button;

    @BindAnim(R.anim.fab_open)
    Animation FabOpen;

    @BindAnim(R.anim.fab_close)
    Animation FabClose;

    @BindAnim(R.anim.rotate_clockwise)
    Animation FabRClockwise;

    @BindAnim(R.anim.rotate_anticlockwise)
    Animation FabRAnticlockwise;

    @BindView(R.id.skillset)
    RecyclerView recyclerView;

    @BindView(R.id.addSkills)
    ImageButton addSkills;

    @BindView(R.id.skill_text)
    TextView skill_text;

    //------------------
    //Variables for voting
    @BindView(R.id.RatingBar)
    ScaleRatingBar ratingBar;


    @BindView(R.id.swipe_refresh_layout_account)
    SwipeRefreshLayout swipeRefreshLayout;

    //------------------
    // Variables for user's information
    @BindView(R.id.profile_name)
    TextView username;

    @BindView(R.id.userNumber)
    TextView userNumber;

    @BindView(R.id.userCity)
    TextView userCity;

    @BindView(R.id.city)
    TextView city;

    @BindView(R.id.number)
    TextView number;

    @BindView(R.id.job_text)
    TextView job_text;

    //private RecyclerView recyclerView;
    @BindView(R.id.recycler_view_jobs)
    RecyclerView recyclerViewJob;

    private List<Job> jobList = new ArrayList<>();

    private JobAdapter jAdapter;

    private boolean isOpen = false;
    final List<Skill> skill;
    final ChipAdapter chipAdapter;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AboutUs.OnFragmentInteractionListener mListener;

    public Account() {
        skill  = new ArrayList<>();
        chipAdapter = new ChipAdapter(skill);
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

        fillUsersData();
        addListenersToObj();
        //-------------------------------------------------------------------------------------
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chipAdapter);

        //--------------------------------------------------------------------------------------
        // Jobs stuffs
        jAdapter = new JobAdapter(jobList);
        RecyclerView.LayoutManager jLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewJob.setLayoutManager(jLayoutManager);
        recyclerViewJob.setItemAnimator(new DefaultItemAnimator());
        recyclerViewJob.setAdapter(jAdapter);

        prepareJobData();

        //--------------------------------------------------------------------------------------
        //Font stuffs
        Typeface nameFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.otf");
        username.setTypeface(nameFont);
        skill_text.setTypeface(nameFont);
        job_text.setTypeface(nameFont);

        Typeface informationFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wider.ttf");
        userNumber.setTypeface(informationFont);
        userCity.setTypeface(informationFont);
        number.setTypeface(informationFont);
        city.setTypeface(informationFont);

        //--------------------------------------------------------------------------------------
        return view;
        // Inflate the layout for this fragment
    }

    private void prepareJobData(){
        Job job = new Job("2015", "SoftServe");
        jobList.add(job);
        Job job1 = new Job("2014", ".UCU");
        jobList.add(job1);

        jAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void plusButtonAnimation(boolean checker){
        if (checker){

            delete_photo.startAnimation(FabClose);
            change_photo.startAnimation(FabClose);
            open_photo.startAnimation(FabClose);
            plus_button.startAnimation(FabRAnticlockwise);

            delete_photo.setClickable(false);
            change_photo.setClickable(false);
            open_photo.setClickable(false);

            isOpen = false;

        }else{
            delete_photo.startAnimation(FabOpen);
            change_photo.startAnimation(FabOpen);
            open_photo.startAnimation(FabOpen);
            plus_button.startAnimation(FabRClockwise);

            delete_photo.setClickable(true);
            change_photo.setClickable(true);
            open_photo.setClickable(true);

            isOpen = true;
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(imageUri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String mediaPath = cursor.getString(columnIndex);


            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            System.err.println(filename+"  " + fileToUpload);
            MainAplication.getServerRequests().updateUserPhoto(fileToUpload)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body()!=null){
                                System.err.println(response.body());
                            }else{
                                System.err.println("RESPONSE BODY NULL");
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            System.err.println("UPDATE PHOTO FAIL " +t.getMessage());
                        }
                    });






            profile_image.setImageURI(imageUri);
        }
    }

    /**
     * Adding Listeners mus`t be done in this function
     * In order to make code more clear
     * 17.02.2018 Victor
     */
    private void addListenersToObj(){

        swipeRefreshLayout.setOnRefreshListener(this);
        addSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDispatcher.launchFragment(SelectSkills.class);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Chat button works", Toast.LENGTH_LONG).show();

            }
        });

        delete_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Delete photo button works", Toast.LENGTH_LONG).show();

            }
        });

        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        open_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Open photo button works", Toast.LENGTH_LONG).show();

            }
        });

        // inflate the layout using the cloned inflater, not default inflater
        //return inflater.inflate(R.layout.fragment_account, container, false);

        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusButtonAnimation(isOpen);
                Toast.makeText(getActivity().getApplicationContext(), "Plus button works", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Method performs refreshing of user information
     * and updated user info
     */
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        MainAplication.getServerRequests().updateUser(new LoginForm(MainAplication.getCurrentUser().getEmail(),
                MainAplication.getCurrentUser().getPassword())).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null){
                    System.err.println("USER UPDATED");
                    MainAplication.saveCurrentUser(response.body());
                    fillUsersData();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                System.err.println("FAILURE");
            }
        });
    }

    /**
     * Fill all user  data in this method
     * Call after every update
     */

    private void fillUsersData(){

        User user = MainAplication.getCurrentUser();

        MainAplication.getServerRequests().getSkills(String.valueOf(user.getId())).enqueue(new Callback<Set<Skill>>() {
            @Override
            public void onResponse(Call<Set<Skill>> call, Response<Set<Skill>> response) {
                if(response.body()!=null) {
                    skill.clear();
                    skill.addAll(response.body());
                    chipAdapter.notifyDataSetChanged();
                }else {
                    //TODO smth if data is null
                }
            }

            @Override
            public void onFailure(Call<Set<Skill>> call, Throwable t) {

                System.err.println("FAILERE DURING DOWNLOADING SKILLS");
            }
        });

        ratingBar.setRating(MainAplication.getCurrentUser().getRating());

        //--------------------------------------------------------------------------------------
        username.setText(user.getName());
        userNumber.setText(user.getMobileNumber());
        userCity.setText(user.getAddress());



    }
}
