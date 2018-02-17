package fatproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.robertlevonyan.views.chip.OnSelectClickListener;

import java.util.ArrayList;
import java.util.List;

import fatproject.activities.MainAplication;
import fatproject.entity.Skill;
import fatproject.findatutor.R;
import fatproject.fragments.SelectSkills;

/**
 * Created by Victor on 17.02.2018.
 */

public class ChipAdapterAllSkills extends RecyclerView.Adapter<ChipAdapterAllSkills.ChipViewHolder>
implements Filterable{

    private List<Skill> skillList;
    private List<Skill> filtredSkillList;


    public class ChipViewHolder extends RecyclerView.ViewHolder {
        public Chip skillChip;

        public ChipViewHolder(View view) {
            super(view);
            skillChip =  view.findViewById(R.id.chipSkill);

        }
    }

    public ChipAdapterAllSkills(List<Skill> skillList) {
        this.skillList = skillList;
        filtredSkillList = skillList;
    }


    @Override
    public ChipAdapterAllSkills.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_in_account, parent, false);

        return new ChipAdapterAllSkills.ChipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChipAdapterAllSkills.ChipViewHolder holder, final int position) {

        String skill = filtredSkillList.get(position).getName();
        System.err.println("SKILL "+skill+" position "+position);
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
                    SelectSkills.getSkillsToAdd().add(filtredSkillList.get(position));
                }else {
                    SelectSkills.getSkillsToAdd().remove(filtredSkillList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  filtredSkillList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String target = charSequence.toString();
                if (target.isEmpty()) {
                    filtredSkillList = skillList;
                } else {
                    List<Skill> list = new ArrayList<>();
                    for (Skill s : skillList) {
                        if (s.getName().toLowerCase().contains(target.toLowerCase())) {
                            list.add(s);
                            System.err.println(s.getName());
                        }
                    }
                    filtredSkillList = list;
                }
                FilterResults results = new FilterResults();
                results.values = filtredSkillList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtredSkillList = (ArrayList<Skill>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

}
