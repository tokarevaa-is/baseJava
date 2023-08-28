package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

public class TextSection extends Section {
    private final String content;

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        TextSection text = (TextSection) obj;

        return content.equals(text.content);
    }

    @Override
    public String toString() {
        return content;
    }
}
