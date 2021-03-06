package fatproject.entity;

/**
 * Created by Max Komarenski on 21.02.2018.
 */

public class Job {

    private Long id;
    private String name;
    private Type type;
    private Position position;

    public Job() {
    }

    public Job(String name) {
        this.name = name;
    }

    public Job(String name, Type type) {
        this.name = name;
        this.type = type;
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

//    public Position getDescription() {
//        return description;
//    }
//
//    public void setDescription(Position description) {
//        this.description = description;
//    }


    public Position getDescription() {
        return position;
    }

    public void setDescription(Position description) {
        this.position = description;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
