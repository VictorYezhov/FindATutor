package fatproject.adapter;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.mrapp.android.view.Chip;
import fatproject.activities.MainAplication;
import fatproject.entity.Skill;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Victor on 13.02.2018.
 */

public class ChipAdapter extends RecyclerView.Adapter<ChipAdapter.ChipViewHolder> {


    private List<Skill> skillList;


    public class ChipViewHolder extends RecyclerView.ViewHolder {
        public Chip skillChip;

        public ChipViewHolder(View view) {
            super(view);
            skillChip =  view.findViewById(R.id.chip);
        }
    }

    public ChipAdapter(List<Skill> skillList) {
        this.skillList = skillList;
    }


    public ChipAdapter() {
        skillList = new ArrayList<>();
    }
    public ChipAdapter(Set<Skill> skillSet){
        this.skillList = new ArrayList<>();
        skillList.addAll(skillSet);
    }

    @Override
    public ChipAdapter.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chip_simple, parent, false);

        return new ChipAdapter.ChipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChipAdapter.ChipViewHolder holder, final int position) {
        final String skill = skillList.get(position).getName();
        final Long skill_id = skillList.get(position).getId();

        holder.skillChip.setText(skill);
        holder.skillChip.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                snackBarDoYouWannaDeleteSkill(holder,skill,position);

                return false;
            }
        });

        holder.skillChip.setTextColor(MainAplication.getContext().getResources().getColor(R.color.white));
        holder.skillChip.setBackgroundColor(MainAplication.getContext().getResources().getColor(R.color.blue));
    }




    @Override
    public int getItemCount() {
        return  skillList.size();
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(Set<Skill> skills)
    {
        this.skillList = new ArrayList<>();
        skillList.addAll(skills);
    }

    public void snackBarDoYouWannaDeleteSkill(final ChipAdapter.ChipViewHolder holder,final String name,final int position){

        Snackbar snackbar = Snackbar.make( holder.itemView ,  "DO YOU WANNA DELETE SKILL " + "'"
                + name + "'", Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        view.setBackgroundColor(Color.LTGRAY);
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);

        snackbar.setAction("Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("adapter:", String.valueOf(position));
                deleteChipOnServerSide(skillList.get(position).getId());
                deleteSkillItem(position);
            }
        }).setActionTextColor(Color.RED);

        snackbar.show();
    }

    public void deleteSkillItem(int position){
        skillList.remove(skillList.get(position));
        notifyItemRemoved(position);
    }

    public void deleteChipOnServerSide(Long skill_id){
        MainAplication.getServerRequests().deleteItemFromAccountChipList(MainAplication.getCurrentUser().getId(), skill_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


}

