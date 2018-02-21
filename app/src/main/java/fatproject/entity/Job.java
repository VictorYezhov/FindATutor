package fatproject.entity;

/**
 * Created by Max Komarenski on 21.02.2018.
 */

public class Job {
    private String year, job;

    public Job(){

    }

    public Job(String year, String job){
        this.year = year;
        this.job = job;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
