package fatproject.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 16.02.2018.
 */

public class Skill {

    private Long id;
    private  String name;
    private Category category;

    public Skill() {

    }

    public Skill(String name) {
        this.name = name;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
