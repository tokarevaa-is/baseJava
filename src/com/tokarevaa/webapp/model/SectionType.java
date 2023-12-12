package com.tokarevaa.webapp.model;

public enum SectionType {
    OBJECTIVE("Позиция", TextSection.class),
    PERSONAL("Личные качества", TextSection.class),
    ACHIEVEMENT("Достижения", ListSection.class),
    QUALIFICATIONS("Квалификация", ListSection.class),
    EXPERIENCE("Опыт работы", OrganizationSection.class),
    EDUCATION("Образование", OrganizationSection.class);

    private final String title;
    private final Class<? extends Section> sectionClass;

    SectionType(String title, Class clazz) {
        this.title = title;
        this.sectionClass = clazz;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends Section> getSectionClass() {
        return sectionClass;
    }
}