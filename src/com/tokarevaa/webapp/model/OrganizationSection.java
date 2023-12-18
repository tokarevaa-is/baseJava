package com.tokarevaa.webapp.model;

import java.time.LocalDate;
import java.util.*;

public class OrganizationSection extends Section {
    private final List<Organization> items;

    public OrganizationSection(List<Organization> items) {
        this.items = items;
    }

    public OrganizationSection() {
        items = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    public List<Organization> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public String toString() {
        return items.toString();
    }

    public void add(Organization org) {
        items.add(org);
    }

    public void addEmpty() {
        add(
                new Organization("", "", Collections.singletonList(
                        new Organization.Position("", "", LocalDate.now(), LocalDate.now()))));
    }
}
