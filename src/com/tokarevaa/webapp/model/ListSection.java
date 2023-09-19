package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.util.DataParser;

import java.util.ArrayList;
import java.util.Collections;
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
    public void parseJson(String json) {
        String newValue = DataParser.extractArray(json);

        String[] textList = DataParser.parseArray(newValue);

        if (items == null) {
            items = new ArrayList<>();
        }
        Collections.addAll(items, textList);
    }

    @Override
    public String toGson() {
        String json = "";
        for (String text : items) {
            json = json + ", {" + text + "}";
        }

        if (json != "") {
            json = json.substring(2);
        }
        json = "[" + json + "]";

        return json;
    }
}
