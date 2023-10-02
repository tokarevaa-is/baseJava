package com.tokarevaa.webapp.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TextSection extends Section {
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public TextSection() {
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

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        if (content == null) {
            dos.writeUTF("");
        } else {
            dos.writeUTF(content);
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        content = dis.readUTF();
    }
}
