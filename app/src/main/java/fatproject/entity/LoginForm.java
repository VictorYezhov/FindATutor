package fatproject.entity;

/**
 * Created by Max Komarenski on 12.02.2018.
 */

public class LoginForm {
    private String email;
    private String password;

   public LoginForm(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
