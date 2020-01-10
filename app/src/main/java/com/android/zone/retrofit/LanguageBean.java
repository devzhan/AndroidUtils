package com.android.zone.retrofit;

public class LanguageBean {

    @Override
    public String toString() {
        return "LanguageBean{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    /**
     * key : browser_boost_rate_content
     * value : 英文文案1
     */

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
