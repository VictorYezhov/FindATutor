package fatproject.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fatproject.entity.Application;
import fatproject.findatutor.R;
import fatproject.fragments.AnswerQuestions;

/**
 * Created by Victor on 01.02.2018.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder>  {


    private List<Application> applicationList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, application_price, category;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category = (TextView) view.findViewById(R.id.application_category);
            application_price= (TextView) view.findViewById(R.id.application_price);

        }
    }

    public ApplicationAdapter(List<Application> applicationList) {
        this.applicationList = applicationList;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_in_answer_question, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.title.setText(application.getTittle());
        holder.application_price.setText(Integer.toString(application.getPrice())+"$");
        holder.category.setText(application.getCategory());
    }

    @Override
    public int getItemCount() {
        return  applicationList.size();
    }



}
