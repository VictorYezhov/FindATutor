package fatproject.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.Helpers.CommentObserver;
import fatproject.IncomingForms.CommentForm;
import fatproject.IncomingForms.QuestionForm;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.adapter.CommentAdapter;
import fatproject.entity.Comment;
import fatproject.findatutor.R;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 01.02.2018.
 */

public class ApplicationDiscription extends Fragment implements CommentObserver {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.userNameAndSureName)
    TextView userNameAndSureName;

    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.priceApplicationDescription)
    TextView price;

    @BindView(R.id.app_discription)
    TextView description;

    @BindView(R.id.description_app_word)
    TextView descriptionWord;

    @BindView(R.id.commentRecycler)
    RecyclerView commentRecycler;

    @BindView(R.id.addCommentButton)
    com.beardedhen.androidbootstrap.BootstrapButton addCommentButton;

    @BindView(R.id.editTextAddComment)
    BootstrapEditText editTextAddComment;

    @BindView(R.id.editTextPriceComment)
    BootstrapEditText editTextPriceComment;

    @BindView(R.id.amount_of_comments)
    com.beardedhen.androidbootstrap.BootstrapButton amountOfComments;

    @BindView(R.id.amount_of_views_question_description)
    com.beardedhen.androidbootstrap.BootstrapButton amountOfViews;

    private CommentAdapter commentAdapter;
    private List<CommentForm> commentList = new ArrayList<>();

    private Contacts.OnFragmentInteractionListener mListener;

    public ApplicationDiscription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contacts.
     */
    // TODO: Rename and change types and number of parameters
    public static Contacts newInstance(String param1, String param2) {
        Contacts fragment = new Contacts();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_appication_discription, container, false);
        ButterKnife.bind(this, view);
        String key = this.getResources().getString(R.string.current_dicription_choise);

        getAllCommentsOfCurrentQuestion();
        final QuestionForm questionForm = Paper.book().read(key);

        if(questionForm.getUserId().equals(MainAplication.getCurrentUser().getId())){
            editTextAddComment.setVisibility(View.INVISIBLE);
            editTextPriceComment.setVisibility(View.INVISIBLE);
            addCommentButton.setVisibility(View.INVISIBLE);
        }

        Typeface grotesk55 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NHaasGroteskTXPro55Rg.ttf");
        Typeface grotesk75 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NHaasGroteskTXPro-75Bd.ttf");
        title.setTypeface(grotesk55);

        String nameStr = questionForm.getUserName() + " " + questionForm.getUserSurname();
        userNameAndSureName.setText(nameStr);
        userNameAndSureName.setTypeface(grotesk55);

        title.setText(questionForm.getQuestion().getTitle());

        String priceStr = questionForm.getQuestion().getPrice().toString() + "$";
        price.setText(priceStr);

        price.setTypeface(grotesk55);

        descriptionWord.setTypeface(grotesk55);

        description.setText(questionForm.getQuestion().getDiscription());
        description.setTypeface(grotesk75);

        //----------Comment RecyclerView-----------------

        commentAdapter = new CommentAdapter(commentList, questionForm.getUserId());
        commentAdapter.registerObserver(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this.getContext());
        layoutManager.setFlexDirection(FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        commentRecycler.setLayoutManager(layoutManager);


        commentRecycler.setLayoutManager(mLayoutManager);

        commentRecycler.setItemAnimator(new DefaultItemAnimator());

        commentRecycler.setAdapter(commentAdapter);

        commentAdapter.notifyDataSetChanged();
        //------------------------------------------------

        String price_str = questionForm.getQuestion().getPrice().toString();
        editTextPriceComment.setText(price_str);

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Comment c = new Comment(editTextAddComment.getText().toString(), Integer.parseInt(editTextPriceComment.getText().toString()), MainAplication.getCurrentUser().getId());
                c.setQuestion(null);
                sendCommentToServer(c , questionForm.getQuestion().getId());

                editTextAddComment.setText("");


                c.setDateTime(new Timestamp(System.currentTimeMillis()));
                CommentForm commentForm = new CommentForm(c, MainAplication.getCurrentUser().getName(),
                                                             MainAplication.getCurrentUser().getFamilyName());
                commentAdapter.addNewComment(commentForm);
                commentAdapter.notifyDataSetChanged();

            }
        });

        QuestionForm qf = Paper.book().read(this.getResources().getString(R.string.current_dicription_choise));

        String number_view = qf.getQuestion().getViews() + " views";

        amountOfViews.setText(number_view);


        return view;
    }



    public void getAllCommentsOfCurrentQuestion(){
        QuestionForm qf = Paper.book().read(this.getResources().getString(R.string.current_dicription_choise));
        MainAplication.getServerRequests().getListOfCommentForms(qf.getQuestion().getId()).enqueue(new Callback<List<CommentForm>>() {
            @Override
            public void onResponse(Call<List<CommentForm>> call, Response<List<CommentForm>> response) {
                System.err.println("RESPONSE FROM COM");
                commentList.clear();
                commentList.addAll(response.body());
                for(int i = 0; i < response.body().size(); i++){
                    System.err.println(response.body().get(i).getComment().getQuestion().getId());
                }
                commentAdapter.notifyDataSetChanged();

                String number_comment = commentList.size() + " comments";
                amountOfComments.setText(number_comment);

            }

            @Override
            public void onFailure(Call<List<CommentForm>> call, Throwable t) {

                System.err.println("COM FAIL");
                t.printStackTrace();
            }
        });

    }


    public void sendCommentToServer(Comment comment, Long questionID){
        MainAplication.getServerRequests().sendNewComment(comment, questionID).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.err.println("something wrong");
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

    @Override
    public void update(String name, String familyName, Long id, Long question_id) {

        openPopUpWindow(name, familyName,id, question_id);
    }

    public void openPopUpWindow(String name, String familyName, Long id, Long question_id){
        System.out.println("click");

        PopupWindowForJobAccepting popup = new PopupWindowForJobAccepting();
        popup.setNameAndFamilyName(name, familyName, id, question_id);
        popup.show(FragmentDispatcher.getFragmentManaget(), "popup");

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
