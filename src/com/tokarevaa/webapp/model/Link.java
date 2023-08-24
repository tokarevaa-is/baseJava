package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.util.Objects;

public class Link {
    private final String name;
    private final String url;

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Link(String url) {
        this.url =
        this.name = url;
    }

    @Override
    public String toString() {
        return "Link(" + name + ", " + url + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        Link link = (Link) obj;

        if (!name.equals(link.name)) return false;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return (31 * name.hashCode() + Assistant.getObjectHash(url));
    }
}