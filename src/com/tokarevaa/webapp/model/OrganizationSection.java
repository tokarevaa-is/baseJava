package com.tokarevaa.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
}
