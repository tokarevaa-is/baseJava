package com.tokarevaa.webapp.model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

public class ResumeTestData {

    private static void checkContactSet(Resume resume, ContactType ct, String s) {
        resume.setContacts(ct, s);
        Assert.assertEquals(resume.getContacts(ct), s);
    }

    private static void checkSectionSet(Resume resume, SectionType st, Section s) {
        resume.setSections(st, s);
        Assert.assertEquals(resume.getSections(st), s);
    }

    private static LocalDate getLastDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        calendar.add(Calendar.DATE, -1);
        int lastDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year, month, lastDayOfMonth);
    }

    public static Resume fillNewResume(String uuid, String fullName) {
        Resume newResume = new Resume(uuid, fullName);

        String mobile = "+7(921) 855-0482";
        String phone = "";
        String skype = "skype:grigory.kislin";
        String email = "gkislin@yandex.ru";
        String linkedin = "https://www.linkedin.com/in/gkislin";
        String github = "https://github.com/gkislin";
        String stackoverflow = "https://stackoverflow.com/users/548473";
        String url = "http://gkislin.ru/";

        checkContactSet(newResume, ContactType.MOBILE, mobile);
        checkContactSet(newResume, ContactType.PHONE, phone);
        checkContactSet(newResume, ContactType.SKYPE, skype);
        checkContactSet(newResume, ContactType.EMAIL, email);
        checkContactSet(newResume, ContactType.LINKEDIN, linkedin);
        checkContactSet(newResume, ContactType.GITHUB, github);
        checkContactSet(newResume, ContactType.STACKOVERFLOW, stackoverflow);
        checkContactSet(newResume, ContactType.URL, url);

        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        Section objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        Section achievement = new ListSection(Arrays.asList(
                "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения", "Java Enterprise", "Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        Section qualifications = new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        ));
        Section experience = new OrganizationSection(Arrays.asList(
                new Organization(
                        "Java Online Projects",
                        "http://javaops.ru/",
                        Collections.singletonList(
                                new Organization.Period(
                                        "Автор проекта.",
                                        "Создание, организация и проведение Java онлайн проектов и стажировок.",
                                        LocalDate.of(2013, 10, 1),
                                        LocalDate.now()
                                )
                        )
                ),
                new Organization(
                        "Wrike",
                        "https://www.wrike.com/",
                        Collections.singletonList(
                                new Organization.Period(
                                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                        "Старший разработчик (backend)",
                                        LocalDate.of(2014, 10, 1),
                                        getLastDay(2016, 1)
                                )
                        )
                ),
                new Organization(
                        "RIT Center",
                        "",
                        Collections.singletonList(
                                new Organization.Period(
                                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                                        "Java архитектор",
                                        LocalDate.of(2012, 4, 1),
                                        getLastDay(2014, 10)
                                )
                        )
                )
        ));
        Section education = new OrganizationSection(Arrays.asList(
                new Organization(
                        "Coursera",
                        "https://www.coursera.org/course/progfun",
                        Collections.singletonList(
                                new Organization.Period(
                                        "Functional Programming Principles in Scala' by Martin Odersky",
                                        "",
                                        LocalDate.of(2013, 3, 1),
                                        getLastDay(2013, 5)
                                )
                        )
                ),
                new Organization(
                        "Luxoft",
                        "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        Collections.singletonList(
                                new Organization.Period(
                                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                                        "",
                                        LocalDate.of(2011, 3, 1),
                                        getLastDay(2011, 4)
                                )
                        )
                ),
                new Organization(
                        "Siemens AG",
                        "http://www.siemens.ru/",
                        Collections.singletonList(
                                new Organization.Period(
                                        "3 месяца обучения мобильным IN сетям (Берлин)",
                                        "",
                                        LocalDate.of(2015, 1, 1),
                                        getLastDay(2015, 4)
                                )
                        )
                ),
                new Organization(
                        "Alcatel",
                        "http://www.alcatel.ru/",
                        Collections.singletonList(
                                new Organization.Period(
                                        "6 месяцев обучения цифровым телефонным сетям (Москва)",
                                        "",
                                        LocalDate.of(1997, 9, 1),
                                        getLastDay(1998, 3)
                                )
                        )
                ),
                new Organization(
                        "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        "http://www.ifmo.ru/",
                        Arrays.asList(
                                new Organization.Period(
                                        "Аспирантура (программист С, С++)",
                                        "",
                                        LocalDate.of(1993, 9, 1),
                                        getLastDay(1996, 7)
                                ),
                                new Organization.Period(
                                        "Инженер (программист Fortran, C)",
                                        "",
                                        LocalDate.of(1987, 9, 1),
                                        getLastDay(1993, 7)
                                )
                        )
                ),
                new Organization(
                        "Заочная физико-техническая школа при МФТИ",
                        "http://www.ifmo.ru/",
                        Collections.singletonList(
                                new Organization.Period(
                                        "",
                                        "Закончил с отличием",
                                        LocalDate.of(1984, 9, 1),
                                        getLastDay(1987, 6)
                                )
                        )
                )
        ));

        checkSectionSet(newResume, SectionType.PERSONAL, personal);
        checkSectionSet(newResume, SectionType.OBJECTIVE, objective);
        checkSectionSet(newResume, SectionType.ACHIEVEMENT, achievement);
        checkSectionSet(newResume, SectionType.QUALIFICATIONS, qualifications);
        checkSectionSet(newResume, SectionType.EXPERIENCE, experience);
        checkSectionSet(newResume, SectionType.EDUCATION, education);

        return newResume;
    }

    @Test
    public void main() {
        Resume resume = fillNewResume("uuid", "Григорий Кислин");

        System.out.println("Resume: " + resume);
        System.out.println("Contacts");
        for (ContactType contact : ContactType.values()) {
            System.out.printf("%s: %s\n", contact.getTitle(), resume.getContacts(contact));
        }

        for (SectionType section : SectionType.values()) {
            System.out.printf("\n\n%s: \n %s\n", section.getTitle(), resume.getSections(section).toString());
        }
    }
}