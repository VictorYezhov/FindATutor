package fatproject.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import fatproject.IncomingForms.QuestionForm;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.entity.Question;
import fatproject.entity.Skill;
import fatproject.findatutor.R;
import fatproject.fragments.AnswerQuestions;
import fatproject.fragments.ApplicationDiscription;
import fatproject.fragments.UserInfo;
import io.paperdb.Paper;

/**
 * Created by Victor on 01.02.2018.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder>  {

    private List<QuestionForm> applicationList;

    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, application_price,  timestamp, contact;
        public RecyclerView recyclerView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            application_price= (TextView) view.findViewById(R.id.application_price);
            timestamp = view.findViewById(R.id.timestamp_answer_questions);
            recyclerView = view.findViewById(R.id.tags_recycle_view);
            contact = view.findViewById(R.id.contact_info);



        }
    }

    public ApplicationAdapter(Context context, List<QuestionForm> applicationList) {
        this.applicationList = applicationList;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_in_answer_question, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int  position) {
        final QuestionForm application = applicationList.get(position);

       // holder.recyclerView.setHasFixedSize(true);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        holder.recyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.recyclerView.setLayoutManager(layoutManager);
        TagAdapter adapter = new TagAdapter(application.getQuestion().getSkills());
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setVisibility(View.VISIBLE);
        StringBuilder name = new StringBuilder(application.getUserName());
        name.append(" ");
        name.append(application.getUserSurname());
        holder.contact.setText(name.toString());
        adapter.notifyDataSetChanged();
        holder.title.setText(application.getQuestion().getTitle());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(application.getQuestion().getDateTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.timestamp.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").
                format(oldFormatedDate));
        holder.application_price.setText("500$");

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionForm application = applicationList.get(position);
                Paper.book().write(MainAplication.getContext().getResources().getString(R.string.current_dicription_choise),
                        application);
                FragmentDispatcher.launchFragment(ApplicationDiscription.class);
               // Toast.makeText(AnswerQuestions.this.getContext(), application.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("user_info", application.getUserId());
                FragmentDispatcher.launchFragment(UserInfo.class);
            }
        });





    }

    @Override
    public int getItemCount() {
        return  applicationList.size();
    }



}
