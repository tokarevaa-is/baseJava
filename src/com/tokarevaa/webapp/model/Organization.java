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
    private List<Position> positions;

    public Organization(String title, String link, List<Position> positions) {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(positions, "Positions must not be null");

        this.title = title;
        if (link != null) {
            this.link = link;
        } else {
            this.link = "";
        }

        this.positions = positions;
    }

    public Organization() {
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public int hashCode() {

        int hash = title.hashCode();
        hash = hash * 31 + Assistant.getObjectHash(link);
        hash = hash * 31 + positions.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        Organization org = (Organization) obj;

        return Objects.equals(title, org.title) &&
                Objects.equals(link, org.link) &&
                Objects.equals(positions, org.positions);
    }

    @Override
    public String toString() {
        return String.format("Organization {Title: %s, Link: %s, Stages: {%s}", title, link, positions);
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
    }
}
