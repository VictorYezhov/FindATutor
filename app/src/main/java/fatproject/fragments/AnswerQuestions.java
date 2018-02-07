package fatproject.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.adapter.ApplicationAdapter;
import fatproject.Helpers.ApplicationListListener;
import fatproject.Helpers.Listener;
import fatproject.activities.FragmentDispatcher;
import fatproject.entity.Application;
import fatproject.findatutor.R;
import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnswerQuestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerQuestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerQuestions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private List<Application> applicationList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ApplicationAdapter mAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;





    public AnswerQuestions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnswerQuestions.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerQuestions newInstance(String param1, String param2) {
        AnswerQuestions fragment = new AnswerQuestions();
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

        View view = inflater.inflate(R.layout.fragment_answer_questions, container, false);
        ButterKnife.bind(this, view);
        Paper.init(this.getContext());


        setApplicationsData();
        mAdapter = new ApplicationAdapter(applicationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new ApplicationListListener(this.getContext(), recyclerView, new Listener() {
            @Override
            public void onClick(View view, int position) {
                Application application= applicationList.get(position);
                Paper.book().write(AnswerQuestions.this.getResources().getString(R.string.current_dicription_choise),
                        application.getDiscription() );
                FragmentDispatcher.launchFragment(ApplicationDiscription.class);
                Toast.makeText(AnswerQuestions.this.getContext(), application.getTittle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);

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


    //TODO: Delete this after testing
    private void setApplicationsData(){
        Application app = new Application("Computer Vision", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_2", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_3", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_4", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_5", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_6", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_7", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_8", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_9", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_10", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_11", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_12", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_13", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_14", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_15", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_16", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_17", "IT","DISCRIPTION",56);
        applicationList.add(app);
        app = new Application("Computer Vision_18", "IT","DISCRIPTION",56);
        applicationList.add(app);

    }



}
