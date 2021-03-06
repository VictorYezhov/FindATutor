package fatproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fatproject.IncomingForms.QuestionForm;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.entity.Question;
import fatproject.findatutor.R;
import fatproject.fragments.ApplicationDiscription;
import io.paperdb.Paper;


public class YourQuestionsAdapter extends RecyclerView.Adapter<YourQuestionsAdapter.MyViewHolder> {
    private List<Question> questions;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, date;
        public RelativeLayout background;
        public com.beardedhen.androidbootstrap.BootstrapButton amountOfViews;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.my_questions_title);
            date = itemView.findViewById(R.id.my_questions_date);
            background = itemView.findViewById(R.id.my_questions_background);
            amountOfViews = itemView.findViewById(R.id.amount_of_views_account);
        }
    }

    public YourQuestionsAdapter(List<Question> questions){
        this.questions = questions;
    }

    @NonNull
    @Override
    public YourQuestionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_question_item_in_account, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YourQuestionsAdapter.MyViewHolder holder, int position) {
        final Question q = questions.get(position);
        final QuestionForm application = new QuestionForm();
        holder.title.setText(q.getTitle());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(q.getDateTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.date.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").
                format(oldFormatedDate));
        String s = q.getViews() + " views";
        holder.amountOfViews.setText(s);

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                application.setQuestion(q);
                application.setUserId(MainAplication.getCurrentUser().getId());
                application.setUserName(MainAplication.getCurrentUser().getName());
                application.setUserSurname(MainAplication.getCurrentUser().getFamilyName());

                Paper.book().write(MainAplication.getContext().getResources().getString(R.string.current_dicription_choise),
                        application);


                FragmentDispatcher.launchFragment(ApplicationDiscription.class);

                }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                application.setQuestion(q);
                application.setUserId(MainAplication.getCurrentUser().getId());
                application.setUserName(MainAplication.getCurrentUser().getName());
                application.setUserSurname(MainAplication.getCurrentUser().getFamilyName());

                Paper.book().write(MainAplication.getContext().getResources().getString(R.string.current_dicription_choise),
                        application);


                FragmentDispatcher.launchFragment(ApplicationDiscription.class);

            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
