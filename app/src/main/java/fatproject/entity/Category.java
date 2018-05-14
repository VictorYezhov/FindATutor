package fatproject.entity;

/**
 * Created by Victor on 14.05.2018.
 */

public class Category {

   private Long id;
   private String name;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
