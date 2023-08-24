package com.tokarevaa.webapp.model;

public enum ContactType {
    MOBILE("Мобильный"),
    PHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverflow"),
    URL("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
