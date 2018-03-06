package fatproject.IncomingForms;

import fatproject.entity.Question;

/**
 * Created by Victor on 06.03.2018.
 */

public class QuestionForm {


    private Question question;
    private Long userId;
    private String userName;
    private String userSurname;

    public QuestionForm() {
    }

    public QuestionForm(Question application, Long userId, String userName, String userSurname) {
        this.question = application;
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question application) {
        this.question = application;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Override
    public String toString() {
        return "QuestionForm{" +
                "question=" + question +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                '}';
    }
}
