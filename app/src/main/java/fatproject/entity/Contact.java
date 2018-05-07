package fatproject.entity;

/**
 * Created by Victor on 06.05.2018.
 */

public class Contact {

    private Long  id;
    private String from;
    private Long idFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }
}
