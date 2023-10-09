package com.tokarevaa.webapp.serializer;

import com.tokarevaa.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public class DataStreamSerializer implements StreamSerializer {

    private <T> void writeCollectionWithException(Collection<T> collection, Consumer<T> consumer, DataOutputStream dos) throws Exception {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(consumer);
        dos.writeInt(collection.size());
        for (T entry : collection) {
            try {
                consumer.accept(entry);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            Consumer<Map.Entry<ContactType, String>> consumer = o -> {
                try {
                    dos.writeUTF(o.getKey().name());
                    dos.writeUTF(o.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
            writeCollectionWithException(contacts.entrySet(), consumer, dos);
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = SectionType.valueOf(entry.getKey().name());
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(entry.getValue().toString());
                        break;

                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ListSection ls = (ListSection) entry.getValue();
                        List<String> items = ls.getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;

                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection org = (OrganizationSection) entry.getValue();
                        dos.writeInt(org.getItems().size());
                        for (Organization organization : org.getItems()) {
                            dos.writeUTF(organization.getTitle());
                            dos.writeUTF(organization.getLink());
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                dos.writeUTF(position.getPosition());
                                dos.writeUTF(position.getDescription());
                                dos.writeUTF(String.valueOf(position.getDateFrom()));
                                dos.writeUTF(String.valueOf(position.getDateTo()));
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.setContact(contactType, value);
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String sectionType = dis.readUTF();
                SectionType st = SectionType.valueOf(sectionType);
                Section section = st.getSection().newInstance();

                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ((TextSection) section).setContent(dis.readUTF());
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int countQualification = dis.readInt();
                        for (int j = 0; j < countQualification; j++) {
                            ((ListSection) section).add(dis.readUTF());
                        }
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        int countEducation = dis.readInt();
                        for (int j = 0; j < countEducation; j++) {
                            List<Organization.Position> positions = new ArrayList<>();
                            Organization organization = new Organization(dis.readUTF(), dis.readUTF(), positions);
                            int positionCount = dis.readInt();
                            while (positionCount > 0) {
                                positions.add(new Organization.Position(dis.readUTF(), dis.readUTF(), LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF())));
                                positionCount--;
                            }
                            ((OrganizationSection) section).add(organization);
                        }
                        break;
                }
                resume.setSection(st, section);
            }
            return resume;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

