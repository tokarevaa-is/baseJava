package com.tokarevaa.webapp.web;

import com.tokarevaa.webapp.Config;
import com.tokarevaa.webapp.model.*;
import com.tokarevaa.webapp.storage.Storage;
import com.tokarevaa.webapp.util.Assistant;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageSQL();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "add":
                resume = new Resume();
                ((OrganizationSection) resume.getSections(SectionType.EDUCATION)).addEmpty();
                ((OrganizationSection) resume.getSections(SectionType.EXPERIENCE)).addEmpty();
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = resume.getSections(type);
                    if (section == null) {
                        try {
                            resume.setSection(type, type.getSectionClass().newInstance());
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (type == SectionType.EDUCATION || type == SectionType.EXPERIENCE) {
                        if (section.toString().equals("[]")) {
                            ((OrganizationSection) section).addEmpty();
                        } else {

                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> orgs = new ArrayList<>();
                            orgs.add(new Organization());
                            for (Organization org : orgSection.getItems()) {
                                List<Organization.Position> position = new ArrayList<>(org.getPositions());
                                org.addPosition(new Organization.Position());
                                orgs.add(new Organization("", org.getLink(), position));
                            }
                            orgs.add(new Organization());
                            section = new OrganizationSection(orgs);
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        boolean error = false;
        Resume resume = null;

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean create = Assistant.isEmpty(uuid);

        if (fullName == null || fullName.isEmpty()) {
            error = true;
        } else {
            if (create) {
                resume = new Resume(fullName);
            } else {
                resume = storage.get(uuid);
                resume.setFullName(fullName);
            }
        }
        if (!error) {
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (Assistant.isEmpty(value)) {
                    resume.getContacts().remove(type);
                } else {
                    resume.setContact(type, value);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                List<Organization> orgs = new ArrayList<>();
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> valuesAsList = new ArrayList<>(Arrays.asList(value.split("\\n")));
                        valuesAsList.removeIf(str -> str.isEmpty() || str.equals("\r"));
                        resume.setSection(type, new ListSection(valuesAsList));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        String[] links = request.getParameterValues(type.name() + "url");
                        String[] values = request.getParameterValues(type.name());
                        for (int i = 0; (i < values.length) && !Assistant.isEmpty(values[i]); i++) {
                            List<Organization.Position> positions = new ArrayList<>();
                            String posIndex = type.name() + i;
                            String orgTitle = values[i];
                            String[] posName = request.getParameterValues(posIndex + "posname");
                            String[] descriptions = request.getParameterValues(posIndex + "description");
                            String[] startDates = request.getParameterValues(posIndex + "startDate");
                            String[] endDates = request.getParameterValues(posIndex + "endDate");
                            for (int j = 0;
                                 (j < posName.length && (!posName[j].isEmpty() || !descriptions[j].isEmpty()));
                                 j++) {
                                positions.add(new Organization.Position(posName[j], descriptions[j], Assistant.parseDate(startDates[j]), Assistant.parseDate(endDates[j])));
                            }
                            orgs.add(new Organization(orgTitle, links[i], positions));
                        }
                        resume.setSection(type, new OrganizationSection(orgs));
                        break;
                }
            }
            if (create) {
                storage.save(resume);
            } else {
                storage.update(resume);
            }
        }
        if (error) {
            if (uuid.isEmpty()) {
                response.sendRedirect("resume?add");
            } else {
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
            }
        } else {
            response.sendRedirect("resume");
        }
    }
}