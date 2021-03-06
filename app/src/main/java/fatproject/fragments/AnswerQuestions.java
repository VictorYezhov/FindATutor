package fatproject.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.dropdownview.DropDownView;
import com.libizo.CustomEditText;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.IncomingForms.QuestionForm;
import fatproject.activities.MainAplication;
import fatproject.adapter.ApplicationAdapter;
import fatproject.Helpers.ApplicationListListener;
import fatproject.Helpers.Listener;
import fatproject.activities.FragmentDispatcher;
import fatproject.entity.Category;
import fatproject.entity.Question;
import fatproject.findatutor.R;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    private List<QuestionForm> applicationList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ApplicationAdapter mAdapter;

    @BindView(R.id.question_search)
    CustomEditText searchView;

    @BindView(R.id.categories)
    DropDownView categories;

    private List<String> categotyNames;
    private List<Category> categoriesList;


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
        categotyNames = new ArrayList<>();

        categoriesList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answer_questions, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new ApplicationAdapter(this.getContext(),applicationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        categories.setActivated(false);

        //setApplicationsData(mAdapter);
        mAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(mAdapter);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        MainAplication.getServerRequests().getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.body()!=null){
                    for(Category c: response.body()){
                        categoriesList.add(c);
                        categotyNames.add(c.getName());
                    }
                    categories.setActivated(true);
                    categories.setDropDownListItem(categotyNames);

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });


        categories.setOnSelectionListener(new DropDownView.OnSelectionListener() {
            @Override
            public void onItemSelected(DropDownView view, int position) {


                setApplicationsData(mAdapter, categoriesList.get(position));
            }
        });





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
    private void setApplicationsData(final ApplicationAdapter mAdapter, Category category){
        MainAplication.getServerRequests().getQuestionsByCategory(category.getId()).enqueue(new Callback<List<QuestionForm>>() {
            @Override
            public void onResponse(Call<List<QuestionForm>> call, Response<List<QuestionForm>> response) {
                if(response.body()!=null){
                    applicationList.clear();
                    applicationList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }else
                System.err.println("NULL");
            }

            @Override
            public void onFailure(Call<List<QuestionForm>> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }



}
