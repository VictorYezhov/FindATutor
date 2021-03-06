package fatproject.adapter;

/**
 * Created by Max Komarenski on 21.02.2018.
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import fatproject.entity.Job;
import fatproject.findatutor.R;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {

    private List<Job> jobsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView place, description;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            place = (TextView) view.findViewById(R.id.place_job);
            description = view.findViewById(R.id.position_description);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

        }
    }


    public JobAdapter(List<Job> jobsList) {
        this.jobsList = jobsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Job job = jobsList.get(position);
        holder.place.setText(job.getName());

        //holder.description.setText(MainAplication.getCurrentUser().getJobs().get(position).getDescription().getName());
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public void removeItem(int position) {
        jobsList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Job item, int position) {
        jobsList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public Long getItem(int position){
        Job j = jobsList.get(position);
        return j.getId();
    }
}
