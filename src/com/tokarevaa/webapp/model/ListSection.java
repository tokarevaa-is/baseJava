package com.tokarevaa.webapp.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> items;

    public ListSection(List<String> items) {
        this.items = items;
    }

    public ListSection() {
    }

    @Override
    public String toString() {
        return items.toString();
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        int count = dis.readInt();
        if (items == null) {
            items = new ArrayList<>();
        }

        while (count > 0) {
            items.add(dis.readUTF());
            count--;
        }
    }
}
