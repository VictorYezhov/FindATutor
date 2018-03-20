package fatproject.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.activities.MainAplication;
import fatproject.entity.Question;
import fatproject.entity.Skill;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskQuestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskQuestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskQuestions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.topic_ask_question)
    BootstrapEditText topicAskQuestion;

    @BindView(R.id.description_ask_question)
    BootstrapEditText descriptionAskQuestion;

    @BindView(R.id.price_ask_question)
    BootstrapEditText priceAskQuestion;

    @BindView(R.id.ButtonAskQuestion)
    BootstrapButton buttonAskQuestion;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AskQuestions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AskQuestions.
     */
    // TODO: Rename and change types and number of parameters
    public static AskQuestions newInstance(String param1, String param2) {
        AskQuestions fragment = new AskQuestions();
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
        // Inflate the layout for this fragment
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Theme_Design_Light);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        // inflate the layout using the cloned inflater, not default inflater
        View view = localInflater.inflate(R.layout.fragment_ask_questions, container, false);

        ButterKnife.bind(this, view);

        buttonAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Set<Skill> skillSetTest = new HashSet<>();

                Integer price;

                try{
                    price = Integer.parseInt(priceAskQuestion.getText().toString());
                }catch (Exception ex){
                    price = -1;
                }

                Question newQuestion = new Question(topicAskQuestion.getText().toString(),
                                                    descriptionAskQuestion.getText().toString(),
                                                    skillSetTest,
                                                    price);

                if(newQuestion.getTitle().equals("")){
                    showSnackbar("Enter the topic of your question.", view);
                }else if(newQuestion.getTitle().length() > 97){
                    showSnackbar("Title should be short. And all details you" +
                            " can explain in description", view);
                    topicAskQuestion.setText("");
                }else if (newQuestion.getDiscription().equals("")){
                    showSnackbar("Enter the description of your question.", view);
                }else if(newQuestion.getPrice() < 0){
                    showSnackbar("Enter the price of your question.", view);
                }else{
                    sendNewQuestion(newQuestion);
                    showSnackbar("Question:  '" + newQuestion.getTitle() +
                            "' Was added.", view);
                    topicAskQuestion.setText("");
                    descriptionAskQuestion.setText("");
                    priceAskQuestion.setText("");
                    skillSetTest.clear();
                }

            }
        });

        return view;

    }

    public void showSnackbar(String element, View view){
        /**
         *This method show on the screen Snackbar (little field in the bottom of user`s screen.)
         */
        Snackbar snackbar;
        snackbar = Snackbar.make(view, element, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.WHITE);
        snackbar.show();
    }

    public void sendNewQuestion(Question question){
        MainAplication.getServerRequests().sendAskingQuestion(question, MainAplication.getCurrentUser().getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
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
