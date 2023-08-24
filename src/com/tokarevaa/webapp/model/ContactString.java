package com.tokarevaa.webapp.model;

public class ContactString extends ContactSection {
    private final String content;

    public ContactString(String s) {
        content = s;
    }

    @Override
    public String toString() {
        return content;
    }
}
