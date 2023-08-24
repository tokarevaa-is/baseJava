package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.util.List;

public class OrganizationSection extends Section {
    private final List<Organization> organizationList;

    public OrganizationSection(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public int hashCode() {
        return organizationList.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        OrganizationSection orgSec = (OrganizationSection) obj;

        return organizationList.equals(orgSec.organizationList);
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }
}
