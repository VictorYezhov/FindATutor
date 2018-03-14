package fatproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fatproject.entity.Job;
import fatproject.findatutor.R;

/**
 * Created by Victor on 14.03.2018.
 */

public class JobAdapterUserInfo  extends RecyclerView.Adapter<JobAdapterUserInfo.MyViewHolder> {

    private List<Job> jobsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView place;

        public MyViewHolder(View view) {
            super(view);
            place = (TextView) view.findViewById(R.id.place_job);
        }
    }


    public JobAdapterUserInfo(List<Job> jobsList) {
        this.jobsList = jobsList;
    }

    @Override
    public JobAdapterUserInfo.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_row, parent, false);

        return new JobAdapterUserInfo.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JobAdapterUserInfo.MyViewHolder holder, int position) {
        Job job = jobsList.get(position);
        holder.place.setText(job.getName());
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }
}
