package fatproject.entity;

/**
 * Created by Victor on 14.05.2018.
 */

public class Category {

   private Long id;
   private String string;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
