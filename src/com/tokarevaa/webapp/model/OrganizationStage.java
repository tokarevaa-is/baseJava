package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.time.LocalDate;
import java.util.Objects;

public class OrganizationStage {

    private final String position;
    private final String description;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public OrganizationStage(String position, String description, LocalDate dateFrom, LocalDate dateTo) {
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

        OrganizationStage stage = (OrganizationStage) obj;
        return Objects.equals(position, stage.position) &&
                Objects.equals(dateFrom, stage.dateFrom) &&
                Objects.equals(dateTo, stage.dateTo);
    }

    @Override
    public String toString() {
        return String.format("Position: %s, from: %s, to: %s", position, dateFrom.toString(), dateTo.toString());
    }
}
