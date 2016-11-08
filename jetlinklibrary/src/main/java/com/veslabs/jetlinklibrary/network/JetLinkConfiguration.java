package com.veslabs.jetlinklibrary.network;

/**
 * Created by Burhan Aras on 10/25/2016.
 */
public class JetLinkConfiguration {
    private String Key;
    private String Value;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "JetLinkConfiguration{" +
                "Key='" + Key + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
