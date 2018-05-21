package fatproject.IncomingForms;

public class QuestionTopicAndPrice {

    private String topic;
    private Integer price;

    public QuestionTopicAndPrice(String topic, Integer price){
        this.topic = topic;
        this.price = price;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
