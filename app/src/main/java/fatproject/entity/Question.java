package fatproject.entity;

/**
 * Created by Victor on 01.02.2018.
 */


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 Class representing application which is shown in a AnswerQuestion Activity
 */
public class Question {


    private Long id;
    private String title;
    private String discription;
    private Timestamp dateTime;


    private Set<Skill> skills;


    public Question(String title, String discription, User user, Set<Skill> skills, Timestamp DateTime) {
        this.title = title;
        this.discription = discription;
        this.skills = skills;
        this.dateTime = DateTime;
    }

    public Question(String title, String discription) {
        this.title = title;
        this.discription = discription;

    }

    public Question() {
        skills = new HashSet<>();
        dateTime = getDateTime();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        discription = discription;
    }




    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", Discription='" + discription + '\'' +
                ", dateTime=" + dateTime +
                ", skills=" + skills +
                '}';
    }
}
