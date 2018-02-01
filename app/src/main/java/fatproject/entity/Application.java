package fatproject.entity;

/**
 * Created by Victor on 01.02.2018.
 */


import java.util.List;

/**
 Class representing application which is shown in a AnswerQuestion Activity
 */
public class Application {

    private String tittle;
    private String category;
    private int price;
    private String discription;
    private List<String> skills;

    public Application(String tittle, String category, int price, List<String> skills) {
        this.tittle = tittle;
        this.category = category;
        this.price = price;
        this.skills = skills;
    }

    public Application(String tittle, String category,String discription, int price) {
        this.tittle = tittle;
        this.category = category;
        this.price = price;
        this.discription = discription;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
