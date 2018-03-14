package fatproject.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.Helpers.ImageSaver;
import fatproject.activities.MainAplication;
import fatproject.adapter.ChipAdapter;
import fatproject.adapter.ChipAdapterUserInfo;
import fatproject.adapter.JobAdapter;
import fatproject.adapter.JobAdapterUserInfo;
import fatproject.entity.Job;
import fatproject.entity.Type;
import fatproject.entity.User;
import fatproject.findatutor.R;
import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Victor on 14.03.2018.
 */

public class UserInfo extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


     @BindView(R.id.profile_image_user_info)
     ImageView profile_image;

     @BindView(R.id.chatButton_user_info)
     FloatingActionButton chatButton;

     @BindView(R.id.skillset_user_info)
     RecyclerView recyclerView;

     @BindView(R.id.skill_text_user_info)
     TextView skill_text;

     @BindView(R.id.RatingBar_user_info)
     ScaleRatingBar ratingBar;

    @BindView(R.id.swipe_refresh_layout_user_info)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.profile_name_user_info)
    TextView username;

    @BindView(R.id.profile_surname_user_info)
    TextView userSurname;

    @BindView(R.id.userNumber_user_info)
    TextView userNumber;

    @BindView(R.id.userCity_user_info)
    TextView userCity;

    @BindView(R.id.job_text_user_info)
    TextView job_text;

    @BindView(R.id.univer_text_user_info)
    TextView univer_text;

    @BindView(R.id.recycler_view_jobs_user_info)
    RecyclerView recyclerViewJob;

    @BindView(R.id.recycler_view_univers_user_info)
    RecyclerView recyclerViewUniver;


    @BindView(R.id.accountScrollView_user_info)
    ScrollView accountScrollView;

    @BindView(R.id.userCoutry_user_info)
    TextView userCountry;

    @BindView(R.id.country_flag_user_info)
    ImageView flag;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private User userInfo;
    private ChipAdapterUserInfo chipAdapter;
    private JobAdapterUserInfo jAdapter;
    private JobAdapterUserInfo uAdapter;

    private AboutUs.OnFragmentInteractionListener mListener;

    public UserInfo() {
        // Required empty public constructor
    }


    public static UserInfo newInstance(String param1, String param2) {
        UserInfo fragment = new UserInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);
        update();
        setFonts();
        swipeRefreshLayout.setOnRefreshListener(this);




        return view;
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
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        update();
        swipeRefreshLayout.setRefreshing(false);

    }

    public  void fillUsersData(){

        chipAdapter = new ChipAdapterUserInfo(userInfo.getSkills());
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chipAdapter);
        chipAdapter.notifyDataSetChanged();

        List<Job> jobs = new ArrayList<>();
        List<Job> edu = new ArrayList<>();

        for (Job j : userInfo.getJobs()) {
            if(j.getType().equals(Type.JOB))
                jobs.add(j);
            else
                edu.add(j);
        }



        jAdapter = new JobAdapterUserInfo(jobs);
        uAdapter = new JobAdapterUserInfo(edu);



        RecyclerView.LayoutManager jLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager uLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewJob.setLayoutManager(jLayoutManager);
        recyclerViewUniver.setLayoutManager(uLayoutManager);

        recyclerViewJob.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUniver.setItemAnimator(new DefaultItemAnimator());

        recyclerViewJob.setAdapter(jAdapter);
        recyclerViewUniver.setAdapter(uAdapter);
        jAdapter.notifyDataSetChanged();
        uAdapter.notifyDataSetChanged();




        ratingBar.setRating(userInfo.getRating());
        username.setText(userInfo.getName());
        userSurname.setText(userInfo.getFamilyName());
        userNumber.setText(userInfo.getMobileNumber());
        userCity.setText(userInfo.getCity().getName());
        userCountry.setText(userInfo.getCity().getCountry().getName());
        Resources resources = this.getContext().getResources();
        loadPhotoFromServer();

        int resourceId =resources.getIdentifier(userInfo.getCity().getCountry().getCode(), "drawable",
                MainAplication.getContext().getPackageName());

        flag.setImageResource(resourceId);




    }


    private  void loadPhotoFromServer(){
        MainAplication.getServerRequests().getUserImage(userInfo.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte byteForm[] = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteForm, 0, byteForm.length);
                    profile_image.setImageBitmap(bitmap);
                }catch (Exception e) {
                    e.printStackTrace();
                    profile_image.setImageResource(R.drawable.noavatar);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void setFonts(){
        Typeface nameFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.otf");
        username.setTypeface(nameFont);
        userSurname.setTypeface(nameFont);

        skill_text.setTypeface(nameFont);
        job_text.setTypeface(nameFont);
        univer_text.setTypeface(nameFont);

        Typeface informationFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wider.ttf");

        userNumber.setTypeface(informationFont);
        userCity.setTypeface(informationFont);
        userCountry.setTypeface(informationFont);
    }

    private void update(){
        Long id = Paper.book().read("user_info");
        MainAplication.getServerRequests().loadUserInfo(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()!=null) {
                    userInfo = response.body();
                    fillUsersData();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.err.println("FAIL DURING LOADING USER INFO");
            }
        });
    }

}
