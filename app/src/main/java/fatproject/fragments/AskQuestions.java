package fatproject.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.asksira.dropdownview.DropDownView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.IncomingForms.QuestionForm;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.adapter.ChipAdapter;
import fatproject.adapter.ChipAdapterAskQuestion;
import fatproject.entity.Category;
import fatproject.entity.Question;
import fatproject.entity.Skill;
import fatproject.findatutor.R;
import fatproject.validation.Validator;
import io.paperdb.Paper;
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

    @BindView(R.id.skillsInNewQuestion)
    RecyclerView recyclerView;

    @BindView(R.id.addSkill)
    AutoCompleteTextView addSkill;

    @BindView(R.id.addTagButton)
    Button addTagButton;

    @BindView(R.id.categories_ask_question)
    DropDownView dropDownView;

    ChipAdapterAskQuestion chipAdapter;

    List<String> skillsNames = new ArrayList<>();


    private List<String> categotyNames;
    private List<Category> categoriesList;

    Question newQuestion = new Question();




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private final ArrayAdapter<String> skillArrayAdapter;
    public AskQuestions() {
        chipAdapter = new ChipAdapterAskQuestion();
        skillArrayAdapter = new ArrayAdapter<>(MainAplication.getContext(),
                android.R.layout.simple_dropdown_item_1line, skillsNames);
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

        categotyNames = new ArrayList<>();

        categoriesList = new ArrayList<>();
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

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(chipAdapter);

        chipAdapter.notifyDataSetChanged();


        dropDownView.setActivated(false);
        dropDownView.setDropDownListItem(categotyNames);

        MainAplication.getServerRequests().getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.body()!=null){
                    for(Category c: response.body()){
                        categoriesList.add(c);
                        categotyNames.add(c.getName());
                    }
                    dropDownView.setActivated(true);
                    dropDownView.setDropDownListItem(categotyNames);

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });



        dropDownView.setOnSelectionListener(new DropDownView.OnSelectionListener() {
            @Override
            public void onItemSelected(DropDownView view, int position) {

                Category c = categoriesList.get(position);
                newQuestion.setCategory(c);
            }
        });




        MainAplication.getServerRequests().getAllAvailableSkills().enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                if(response.body()!=null) {
                    for (Skill s:
                         response.body()) {
                        skillsNames.add(s.getName());
                    }
                    addSkill.setAdapter(skillArrayAdapter);
                    skillArrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {

                System.err.println("FAILERE DURING DOWNLOADING SKILLS");
            }
        });

        setActionsToElements();



        return view;

    }

    private void setActionsToElements(){


        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Skill skill = new Skill(addSkill.getText().toString());
                chipAdapter.getSkillList().add(skill);

                chipAdapter.notifyDataSetChanged();
                addSkill.setText("");

            }
        });

        buttonAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Set<Skill> skillSetTest = new HashSet<>(chipAdapter.getSkillList());
                Integer price;
                try{
                    price = Integer.parseInt(priceAskQuestion.getText().toString());
                }catch (Exception ex){
                    price = -1;
                }

                newQuestion.setTitle(topicAskQuestion.getText().toString());
                newQuestion.setDiscription(descriptionAskQuestion.getText().toString());
                newQuestion.setSkills(skillSetTest);
                newQuestion.setPrice(price);
                newQuestion.setViews(0);
                newQuestion.setDateTime(new Timestamp(Calendar.getInstance().getTime().getTime()));

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
                    chipAdapter.notifyDataSetChanged();
                }
            }
        });
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
        MainAplication.getServerRequests().sendAskingQuestion(question, MainAplication.getCurrentUser().getId()).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                System.err.println(response.body());
                question.setId(response.body());
                QuestionForm qf = new QuestionForm();
                qf.setUserName(MainAplication.getCurrentUser().getName());
                qf.setUserSurname(MainAplication.getCurrentUser().getFamilyName());
                qf.setUserId(MainAplication.getCurrentUser().getId());
                qf.setQuestion(question);
                Paper.book().write(MainAplication.getContext().getResources().getString(R.string.current_dicription_choise),
                        qf);
                FragmentDispatcher.launchFragment(ApplicationDiscription.class);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

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
