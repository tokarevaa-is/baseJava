package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.util.Assistant;
import com.tokarevaa.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private String title;
    private String link;
    private List<Position> stage;

    public Organization(String title, String link, List<Position> stage) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(stage, "Stages must not be null");

        this.title = title;
        this.link = link;
        this.stage = stage;
    }

    public Organization() {
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

    public String toJson() {
        return String.format("{%s}, {%s}, {[%s]}", title, link, stageToJson());
    }

    private String stageToJson() {
        String json = "";

        for (Position position : stage) {
            json = json + ", {" + position.toJson() + "}";
        }

        if (json != "") {
            json = json.substring(2);
        }

        return json;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        private String position;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;

        public Position(String position, String description, LocalDate dateFrom, LocalDate dateTo) {
            this.position = position;
            this.description = description;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        public Position() {
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(this.position, position.position) &&
                    Objects.equals(description, position.description) &&
                    Objects.equals(dateFrom, position.dateFrom) &&
                    Objects.equals(dateTo, position.dateTo);
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        @Override
        public String toString() {
            return String.format("Position: %s, from: %s, to: %s", position, dateFrom.toString(), dateTo.toString());
        }

        public String toJson() {
            return String.format("{%s}, {%s}, {%s}, {%s}",
                    position, description, dateFrom, dateTo);
        }
    }
}
