package fatproject.SendingForms;

import java.util.List;

import fatproject.entity.Skill;

/**
 * Created by Victor on 17.02.2018.
 */

public class SendSkillsForm {

    private List<Skill> skills;
    private Long userId;

    public SendSkillsForm(List<Skill> skills, Long userId) {
        this.skills = skills;
        this.userId = userId;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
