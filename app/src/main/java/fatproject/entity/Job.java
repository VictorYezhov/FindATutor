package fatproject.entity;

/**
 * Created by Max Komarenski on 21.02.2018.
 */

public class Job {

    private Long id;
    private String name;


    public Job() {
    }

    public Job(String name) {
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
