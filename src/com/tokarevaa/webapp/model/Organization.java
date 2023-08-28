package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final String title;
    private final Link link;
    private final List<OrganizationStage> stage;

    public Organization(String title, String link, List<OrganizationStage> stage) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(stage, "Stages must not be null");

        this.title = title;
        this.link = new Link(title, link);
        this.stage = stage;
    }

    @Override
    public int hashCode() {

        int hash = title.hashCode();
        hash = hash * 31 + Assistant.getObjectHash(link);
        hash = hash * 31 + stage.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        Organization org = (Organization) obj;

        return title.equals(org.title) &&
                link.equals(org.link) &&
                Objects.equals(stage, org.stage);
    }

    @Override
    public String toString() {
        return String.format("Organization {Title: %s, Link: %s, Stages: {%s}", title, link, stage);
    }
}
