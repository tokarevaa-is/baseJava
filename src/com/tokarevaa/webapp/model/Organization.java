package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Organization {

    private final String title;
    private final String link;
    private final List<Period> stage;

    public Organization(String title, String link, List<Period> stage) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(stage, "Stages must not be null");

        this.title = title;
        this.link = link;
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

    public static class Period {

        private final String position;
        private final String description;
        private final LocalDate dateFrom;
        private final LocalDate dateTo;

        public Period(String position, String description, LocalDate dateFrom, LocalDate dateTo) {
            this.position = position;
            this.description = description;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        @Override
        public int hashCode() {
            int hash = Assistant.getObjectHash(position);
            hash = hash * 31 + Assistant.getObjectHash(description);
            hash = hash * 31 + dateFrom.hashCode();
            hash = hash * 31 + dateTo.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (!Assistant.isObjectEqualsBasic(this, obj)) {
                return false;
            }

            Period stage = (Period) obj;
            return Objects.equals(position, stage.position) &&
                    Objects.equals(dateFrom, stage.dateFrom) &&
                    Objects.equals(dateTo, stage.dateTo);
        }

        @Override
        public String toString() {
            return String.format("Position: %s, from: %s, to: %s", position, dateFrom.toString(), dateTo.toString());
        }
    }
}
