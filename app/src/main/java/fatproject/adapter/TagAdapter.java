package fatproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import de.mrapp.android.view.Chip;
import fatproject.activities.MainAplication;
import fatproject.entity.Skill;
import fatproject.findatutor.R;

/**
 * Created by Victor on 07.03.2018.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ChipViewHolder> {

    private List<Skill> skillList;


    public class ChipViewHolder extends RecyclerView.ViewHolder {
        public Chip skillChip;

        public ChipViewHolder(View view) {
            super(view);
            skillChip =  view.findViewById(R.id.chip);
        }
    }

    public TagAdapter(List<Skill> skillList) {
        this.skillList = skillList;
    }


    public TagAdapter() {
    }
    public TagAdapter(Set<Skill> skillSet){
        this.skillList = new ArrayList<>();
        skillList.addAll(skillSet);
    }

    @Override
    public TagAdapter.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chip_simple, parent, false);

        return new TagAdapter.ChipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TagAdapter.ChipViewHolder holder, int position) {
        String skill = skillList.get(position).getName();

        holder.skillChip.setText(skill);
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
}
