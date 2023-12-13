package com.tokarevaa.webapp.web;

import com.tokarevaa.webapp.Config;
import com.tokarevaa.webapp.model.*;
import com.tokarevaa.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageSQL();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
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
        boolean create = isEmpty(uuid);

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
                if (isEmpty(value)) {
                    resume.getContacts().remove(type);
                } else {
                    resume.setContact(type, value);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                if (isEmpty(value) && (type != SectionType.EDUCATION && type != SectionType.EXPERIENCE)) {
                    error = true;
                    break;
                } else {
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            resume.setSection(type, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            resume.setSection(type, new ListSection(Arrays.asList(value.split("\\n"))));
                            break;
                    }
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