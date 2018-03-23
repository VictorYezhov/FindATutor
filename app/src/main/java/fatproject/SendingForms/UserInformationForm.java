package fatproject.SendingForms;

/**
 * Created by Max Komarenski on 02.03.2018.
 */

public class UserInformationForm {
    private String number;
    private String city;
    private String country;


    public UserInformationForm() {
    }

    public UserInformationForm(String number, String city, String country){
        this.number = number;
        this.city = city;
        this.city = country;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
