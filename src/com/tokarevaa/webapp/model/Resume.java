package com.tokarevaa.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume() {
        try {
            this.setSection(SectionType.OBJECTIVE, SectionType.OBJECTIVE.getSectionClass().newInstance());
            this.setSection(SectionType.PERSONAL, SectionType.PERSONAL.getSectionClass().newInstance());
            this.setSection(SectionType.ACHIEVEMENT, SectionType.ACHIEVEMENT.getSectionClass().newInstance());
            this.setSection(SectionType.QUALIFICATIONS, SectionType.QUALIFICATIONS.getSectionClass().newInstance());
            this.setSection(SectionType.EXPERIENCE, SectionType.EXPERIENCE.getSectionClass().newInstance());
            this.setSection(SectionType.EDUCATION, SectionType.EDUCATION.getSectionClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void setContact(ContactType contact, String value) {
        contacts.put(contact, value);
    }

    public String getContacts(ContactType contact) {
        return contacts.get(contact);
    }

    public Section getSections(SectionType section) {
        return sections.get(section);
    }

    public void setSection(SectionType section, Section value) {
        sections.put(section, value);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}