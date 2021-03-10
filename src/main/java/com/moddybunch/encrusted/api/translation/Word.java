package com.moddybunch.encrusted.api.translation;

/**
 * A word, storing both a translation key and a value
 */
public class Word {

    private String key;
    private String value;

    public Word(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
