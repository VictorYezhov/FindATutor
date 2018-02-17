package fatproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.robertlevonyan.views.chip.OnSelectClickListener;

import java.util.List;

import fatproject.activities.MainAplication;
import fatproject.entity.Skill;
import fatproject.findatutor.R;
import fatproject.fragments.SelectSkills;

/**
 * Created by Victor on 17.02.2018.
 */

public class ChipAdapterAllSkills extends RecyclerView.Adapter<ChipAdapterAllSkills.ChipViewHolder>  {

    private List<Skill> skillList;


    public class ChipViewHolder extends RecyclerView.ViewHolder {
        public Chip skillChip;

        public ChipViewHolder(View view) {
            super(view);
            skillChip =  view.findViewById(R.id.chipSkill);

        }
    }

    public ChipAdapterAllSkills(List<Skill> skillList) {
        this.skillList = skillList;
    }


    @Override
    public ChipAdapterAllSkills.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_in_account, parent, false);

        return new ChipAdapterAllSkills.ChipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChipAdapterAllSkills.ChipViewHolder holder, final int position) {
        String skill = skillList.get(position).getName();
        holder.skillChip.setChipText(skill);
        holder.skillChip.setSelectable(true);
        holder.skillChip.setSelected(true);
        holder.skillChip.setTextColor(MainAplication.getContext().getResources().getColor(R.color.accent));
        holder.skillChip.changeBackgroundColor(MainAplication.getContext().getResources().getColor(R.color.blue));
        holder.skillChip.setOnSelectClickListener(new OnSelectClickListener() {
            @Override
            public void onSelectClick(View v, boolean selected) {

                System.err.println(selected);
                if(!selected) {
                    SelectSkills.getSkillsToAdd().add(skillList.get(position));
                }else {
                    SelectSkills.getSkillsToAdd().remove(skillList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  skillList.size();
    }


}
