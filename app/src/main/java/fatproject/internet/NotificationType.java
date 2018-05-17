package fatproject.internet;

/**
 * Created by Victor on 17.05.2018.
 */

public enum NotificationType {

    PERSONALMESSAGE("0"), NEWCONTRACT("1"), CONTRACTCHANGE("2");

    String type;

    NotificationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
