package com.tokarevaa.webapp.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeInt(organizationList.size());
        for (Organization org : organizationList) {
            org.writeData(dos);
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        int count = dis.readInt();
        organizationList = new ArrayList<>();

        while (count > 0){
            Organization organization = new Organization();
            organization.readData(dis);
            organizationList.add(organization);
            count--;
        }
    }
}
