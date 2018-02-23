package fatproject.entity;

/**
 * Created by Max Komarenski on 21.02.2018.
 */

public class Job {

    private Long id;
    private String name;
    private String year;


    public Job() {
    }

    public Job(String name, String year) {
        this.name = name;
        this.year = year;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
