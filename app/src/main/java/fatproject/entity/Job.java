package fatproject.entity;

/**
 * Created by Max Komarenski on 21.02.2018.
 */

public class Job {

    private Long id;
    private String name;
    private Type type;

    private String description;

    public Job() {
    }

    public Job(String name) {
        this.name = name;
    }

    public Job(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Job(String name, Type type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
