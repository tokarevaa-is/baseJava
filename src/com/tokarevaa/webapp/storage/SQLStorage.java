package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.model.ContactType;
import com.tokarevaa.webapp.model.Resume;
import com.tokarevaa.webapp.model.Section;
import com.tokarevaa.webapp.model.SectionType;
import com.tokarevaa.webapp.sql.SQLHelper;
import com.tokarevaa.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {

    private final SQLHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContactsDB(connection, r);
            insertSectionsDB(connection, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {

            Resume resume;

            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement psc = connection.prepareStatement(
                    "SELECT * FROM contact WHERE resume_uuid = ?")) {
                psc.setString(1, uuid);
                ResultSet rsc = psc.executeQuery();
                while (rsc.next()) {
                    addContact(rsc, resume);
                }
            }

            try (PreparedStatement pss = connection.prepareStatement(
                    "SELECT * FROM section WHERE resume_uuid = ?")) {
                pss.setString(1, uuid);
                ResultSet rss = pss.executeQuery();
                while (rss.next()) {
                    addSection(rss, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume r SET full_name = ? WHERE r.uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContactsDB(connection, resume);
            insertContactsDB(connection, resume);
            deleteSectionsDB(connection, resume);
            insertSectionsDB(connection, resume);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * " +
                    "  FROM resume " +
                    "  ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resumes.get(rs.getString("resume_uuid")));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resumes.get(rs.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.setContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.setSection(
                    SectionType.valueOf(rs.getString("type")),
                    JsonParser.read(value, Section.class));
        }
    }

    private void insertContactsDB(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContactsDB(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertSectionsDB(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, JsonParser.write(e.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSectionsDB(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}