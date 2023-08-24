package com.tokarevaa.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, ContactSection> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContacts(ContactType contact, ContactSection value) {
        contacts.put(contact, value);
    }

    public ContactSection getContacts(ContactType contact) {
        return contacts.get(contact);
    }

    public Section getSections(SectionType section) {
        return sections.get(section);
    }

    public void setSections(SectionType section, Section value) {
        sections.put(section, value);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return !uuid.equals(resume.uuid) || fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return (uuid + fullName).hashCode();
    }

    @Override
    public String toString() {
        return (fullName + "(" + uuid + ")");
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }
}
