package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {

    private final String title;
    private final String description;
    private final String position;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final Link link;

    public Organization(String title, String description, String position, LocalDate dateFrom, LocalDate dateTo, String link) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(dateFrom, "Date From must not be null");
        Objects.requireNonNull(dateTo, "Date To must not be null");

        this.title = title;
        this.description = description;
        this.position = position;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.link = new Link(title, link);
    }

    @Override
    public int hashCode() {

        int hash = title.hashCode();
        hash = hash * 31 + Assistant.getObjectHash(description);
        hash = hash * 31 + Assistant.getObjectHash(position);
        hash = hash * 31 + dateFrom.hashCode();
        hash = hash * 31 + dateTo.hashCode();
        hash = hash * 31 + Assistant.getObjectHash(link);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        Organization org = (Organization) obj;

        return title.equals(org.title) &&
                Objects.equals(description, org.description) &&
                description.equals(org.description) &&
                Objects.equals(position, org.position) &&
                dateFrom.equals(org.dateFrom) &&
                dateTo.equals(org.dateTo) &&
                Objects.equals(link, org.link);
    }

    @Override
    public String toString() {
        return "Organization {Title: " + title +
                " Link: " + link +
                " Position: " + position +
                " From: " + dateFrom +
                " To: " + dateTo +
                " Description: " + description;
    }
}
