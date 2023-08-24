package com.tokarevaa.webapp.model;

public class ContactLink extends ContactSection {
    private final Link content;

    public ContactLink(Link s) {
        content = s;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
