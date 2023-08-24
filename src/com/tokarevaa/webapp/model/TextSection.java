package com.tokarevaa.webapp.model;

import com.tokarevaa.webapp.assist.Assistant;

public class TextSection extends Section {
    private final String textContent;

    public TextSection(String content) {
        textContent = content;
    }

    @Override
    public int hashCode() {
        return textContent.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!Assistant.isObjectEqualsBasic(this, obj)) {
            return false;
        }

        TextSection text = (TextSection) obj;

        return textContent.equals(text.textContent);
    }

    @Override
    public String toString() {
        return textContent;
    }
}
