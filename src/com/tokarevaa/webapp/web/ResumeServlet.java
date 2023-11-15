package com.tokarevaa.webapp.web;

import com.tokarevaa.webapp.Config;
import com.tokarevaa.webapp.model.Resume;
import com.tokarevaa.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageSQL();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8 ");

        Writer writer = response.getWriter();
        writer.write("" +
                "<html>\n" +
                "<header><h2>Resumes:</h2></header>\n" +
                "<body>\n" +
                "<table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<td>Resume ID</td>\n" +
                "<td>Full name</td>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n");

        for (Resume resume : storage.getAllSorted()) {
            writer.write("" +
                    "<tr>\n" +
                    "<td>" + resume.getUuid() + "</td>\n" +
                    "<td>" + resume.getFullName() + "</td>\n" +
                    "</tr>\n"
            );
        }

        writer.write("" +
                "</tbody>\n" +
                "</table>\n" +
                "</body>" +
                "</html>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
    }
}