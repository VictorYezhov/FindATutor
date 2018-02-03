package fatproject.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 03.02.2018.
 * ONLY FOR HTTP CONNECTION TEST
 */

public class Greeting {

    private Integer id;
    private String content;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
