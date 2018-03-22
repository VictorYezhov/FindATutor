package fatproject.entity;

import java.util.List;

/**
 * Created by Victor on 16.03.2018.
 */

public class Position {

    private Long id;
    private String name;
    //private List<Job> jobs;

    public Position() {
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

//    public List<Job> getJobs() {
//        return jobs;
//    }
//
//    public void setJobs(List<Job> jobs) {
//        this.jobs = jobs;
//    }
}
