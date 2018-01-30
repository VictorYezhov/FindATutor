package fatproject.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;


import butterknife.Bind;
import butterknife.ButterKnife;
import fatproject.findatutor.R;

/**
 * Created by Victor on 30.01.2018.
 */

public class Account extends Fragment {

    @Bind(R.id.chatButton)
    ImageButton chatButton;

    @Bind(R.id.open_photo)
    FloatingActionButton open_photo;

    @Bind(R.id.delete_photo)
    FloatingActionButton delete_photo;

    @Bind(R.id.change_photo)
    FloatingActionButton change_photo;

    @Bind(R.id.plus_button)
    FloatingActionButton plus_button;

    @Bind(R.anim.fab_open)
    Animation FabOpen;

    @Bind(R.anim.fab_close)
    Animation FabClose;

    @Bind(R.anim.rotate_clockwise)
    Animation FabRClockwise;

    @Bind(R.anim.rotate_anticlockwise)
    Animation FabRAnticlockwise;

    private boolean isOpen = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AboutUs.OnFragmentInteractionListener mListener;

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutUs.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutUs newInstance(String param1, String param2) {
        AboutUs fragment = new AboutUs();
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

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("chat_button pressed");
            }
        });

        // inflate the layout using the cloned inflater, not default inflater
        //return inflater.inflate(R.layout.fragment_account, container, false);

        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpen){

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
        });

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
