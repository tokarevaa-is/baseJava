package com.tokarevaa.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        this.items = items;
    }

    public ListSection() {
        this("");
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
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

    public void add(String string) {
        if (string != null) {
            items.add(string);
        }
    }
}
