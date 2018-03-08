package fatproject.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import fatproject.activities.MainAplication;
import fatproject.entity.Question;
import fatproject.entity.Skill;
import fatproject.findatutor.R;

/**
 * Created by Victor on 01.02.2018.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder>  {

    private List<QuestionForm> applicationList;

    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, application_price,  timestamp;
        public RecyclerView recyclerView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            application_price= (TextView) view.findViewById(R.id.application_price);
            timestamp = view.findViewById(R.id.timestamp_answer_questions);
            recyclerView = view.findViewById(R.id.tags_recycle_view);


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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Question application = applicationList.get(position).getQuestion();

       // holder.recyclerView.setHasFixedSize(true);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        holder.recyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        holder.recyclerView.setLayoutManager(layoutManager);
        TagAdapter adapter = new TagAdapter(application.getSkills());
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();


        holder.title.setText(application.getTitle());


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(application.getDateTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.timestamp.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").
                format(oldFormatedDate));
        holder.application_price.setText("50$");
    }

    @Override
    public int getItemCount() {
        return  applicationList.size();
    }



}
