package fatproject.internet;

/**
 * Created by Victor on 03.02.2018.
 */

public enum URLs {

    BASE("http://10.0.2.2:8080/");

    private String url;

    URLs(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
