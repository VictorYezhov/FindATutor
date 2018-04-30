package fatproject.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import fatproject.activities.MainAplication;
import fatproject.entity.Job;
import fatproject.findatutor.R;

public class UniverAdapter extends RecyclerView.Adapter<UniverAdapter.MyViewHolder>{

    private List<Job> universList;


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


    public UniverAdapter(List<Job> universList) {
        this.universList = universList;
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
        Job univer = universList.get(position);
        holder.place.setText(univer.getName());

        //holder.description.setText(MainAplication.getCurrentUser().getJobs().get(position).getDescription().getName());
    }

    @Override
    public int getItemCount() {
        return universList.size();
    }

    public void removeItem(int position) {
        universList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Job item, int position) {
        universList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public Long getItem(int position){
        Job j = universList.get(position);
        return j.getId();
    }
}

