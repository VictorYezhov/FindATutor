package fatproject.IncomingForms;

import fatproject.entity.Comment;

/**
 * Created by HP on 22.03.2018.
 */

public class CommentForm {
    private Comment comment;
    private String userName;
    private String userSurname;
    //byte[] image;

    public CommentForm(Comment comment, String userName, String userSurname){
        this.comment = comment;
        this.userName = userName;
        this.userSurname = userSurname;
    }


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }
}
