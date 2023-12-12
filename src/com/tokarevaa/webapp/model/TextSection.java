package com.tokarevaa.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private String content;

    public TextSection(String content) {
        if (content != null) this.content = content;
        else this.content = "";
    }

    public TextSection() {
        content = "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public String toString() {
        return content != null ? content : "";
    }
}
