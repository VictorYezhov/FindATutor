package fatproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fatproject.entity.Appointment;
import fatproject.entity.Job;
import fatproject.findatutor.R;

/**
 * Created by Victor on 17.05.2018.
 */

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MyViewHolder> {


    private List<Appointment> appointments;

    public ContractsAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView place;

        public MyViewHolder(View view) {
            super(view);
         //   place = (TextView) view.findViewById(R.id.place_job);
        }
    }

    @Override
    public ContractsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_single, parent, false);

        return new ContractsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContractsAdapter.MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        //holder.place.setText("app");
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }



}
