package fatproject.entity;

/**
 * Created by Victor on 01.02.2018.
 */


import android.content.Intent;

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
    private Integer price;

    private Set<Skill> skills;

    public Question(){
        this.id = null;
        this.title = "Title";
        this.dateTime = getDateTime();

        this.skills = new HashSet<>();
    }

    public Question(String title, String discription, Set<Skill> skills, Integer price) {
        this.title = title;
        this.discription = discription;
        this.skills = skills;
        this.price = price;
    }



    public Question(String title, String discription){
        this.title = title;
        this.discription = discription;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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
                ", discription='" + discription + '\'' +
                ", dateTime=" + dateTime +
                ", skills=" + skills +
                '}';
    }
}
