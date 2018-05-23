package fatproject.internet;

/**
 * Created by Victor on 03.02.2018.
 */

public enum URLs {

    BASE_LOCKAL("http://10.0.2.2:8080/"),
    BASE_REMOTE("http://138.68.79.221:8080/"),
    BASE_REMOTE_OUR("http://206.189.61.135:8080/");

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
