package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.util.DataParser;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private List<Organization> organizationList;

    public OrganizationSection(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public OrganizationSection() {
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

    @Override
    public void parseJson(String json) {
        String[] newOrganization = DataParser.parseArray(DataParser.extractArray(json));
    }

    @Override
    public String toGson() {
        String json = "";

        for (Organization org : organizationList) {
            json = json + ", {" + org.toJson() + "}";
        }

        if (json != "") {
            json = json.substring(2);
        }

        json = "[" + json + "]";

        return json;
    }
}
