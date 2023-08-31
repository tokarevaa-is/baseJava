package com.tokarevaa.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private final List<Organization> organizationList;

    public OrganizationSection(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public int hashCode() {
        return organizationList.hashCode();
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }
}
