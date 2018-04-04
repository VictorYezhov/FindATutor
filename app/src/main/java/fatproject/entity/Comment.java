package fatproject.entity;

import java.sql.Timestamp;

/**
 * Created by Komarenski Max on 21.03.2018.
 */

public class Comment {
    private Long id;
    private Timestamp dateTime;
    private String textComment;
    private Integer price;
    private Long userId;

    private Question question;

    public Comment(String textComment, Integer price, Long userId) {
        this.dateTime = null;
        this.textComment = textComment;
        this.price = price;
        this.userId = userId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
