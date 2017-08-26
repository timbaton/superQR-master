package info.superego.qrreader.models;

import java.util.HashMap;
import java.util.Map;

public class Data {

    private String email;
    private String name;
    private String phone;
    private String buy_time;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBuyTime() {
        return buy_time;
    }

    public void setBuyTime(String buyTime) {
        this.buy_time = buyTime;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
