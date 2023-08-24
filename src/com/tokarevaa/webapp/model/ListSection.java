package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

import java.util.List;

public class ListSection extends Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        ListSection listObj = (ListSection) obj;

        return items.equals(listObj.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
