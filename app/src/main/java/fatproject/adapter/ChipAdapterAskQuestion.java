package fatproject.adapter;

import android.support.annotation.NonNull;
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
 * Created by Victor on 05.04.2018.
 */

public class ChipAdapterAskQuestion extends RecyclerView.Adapter<ChipAdapterAskQuestion .ChipViewHolder> {


    private List<Skill> skillList;


    public class ChipViewHolder extends RecyclerView.ViewHolder {
        public Chip skillChip;

        public ChipViewHolder(View view) {
            super(view);
            skillChip =  view.findViewById(R.id.chip);
            skillChip.setClosable(true);
        }
    }

    public ChipAdapterAskQuestion (List<Skill> skillList) {
        this.skillList = skillList;
    }


    public ChipAdapterAskQuestion () {
        skillList = new ArrayList<>();
    }
    public ChipAdapterAskQuestion (Set<Skill> skillSet){
        this.skillList = new ArrayList<>();
        skillList.addAll(skillSet);
    }

    @Override
    public ChipAdapterAskQuestion .ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chip_simple, parent, false);

        return new ChipAdapterAskQuestion .ChipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChipAdapterAskQuestion .ChipViewHolder holder, final int position) {
        String skill = skillList.get(position).getName();

        holder.skillChip.setText(skill);
        holder.skillChip.setTextColor(MainAplication.getContext().getResources().getColor(R.color.white));
        holder.skillChip.setBackgroundColor(MainAplication.getContext().getResources().getColor(R.color.blue));
        holder.skillChip.addCloseListener(new Chip.CloseListener() {
            @Override
            public void onChipClosed(@NonNull Chip chip) {
                if(skillList.size() == 0){
                    skillList.clear();
                    notifyDataSetChanged();
                }else {
                    skillList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
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
