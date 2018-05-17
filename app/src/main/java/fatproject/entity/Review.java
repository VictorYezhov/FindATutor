package fatproject.entity;

public class Review {
    private Long id;
    private String review;
    private Long about;
    private Long from;
    private boolean anonymous;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getAbout() {
        return about;
    }

    public void setAbout(Long about) {
        this.about = about;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

}
