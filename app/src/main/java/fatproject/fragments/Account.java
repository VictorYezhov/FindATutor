package fatproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.robertlevonyan.views.chip.OnIconClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindAnim;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.adapter.ChipAdapter;
import fatproject.entity.Skill;
import fatproject.entity.User;
import fatproject.findatutor.R;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Victor on 30.01.2018.
 */

public class Account extends Fragment {

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
    Chip addSkillsChip;




    @BindView(R.id.profile_name)
    TextView username;

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

        User user = Paper.book().read("currentUser");

        MainAplication.getServerRequests().getSkills(String.valueOf(user.getId())).enqueue(new Callback<Set<Skill>>() {
            @Override
            public void onResponse(Call<Set<Skill>> call, Response<Set<Skill>> response) {
                if(response.body()!=null) {
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
        addListenersToObj();
        //-----------------------------------------------------------------------
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chipAdapter);

        //--------------------------------------------------------------------------------------

        username.setText(user.getName());

        //--------------------------------------------------------------------------------------


        return view;

        // Inflate the layout for this fragment
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
            profile_image.setImageURI(imageUri);
        }
    }


    /**
     * Adding Listeners mus`t be done in this function
     * In order to make code more clear
     * 17.02.2018 Victor
     */
    private void addListenersToObj(){



        addSkillsChip.setOnIconClickListener(new OnIconClickListener() {
            @Override
            public void onIconClick(View v) {
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


}
