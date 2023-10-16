package com.tokarevaa.webapp.serializer;

import com.tokarevaa.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(contacts.entrySet(), dos, o -> {
                dos.writeUTF(o.getKey().name());
                dos.writeUTF(o.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();

            writeWithException(sections.entrySet(), dos, o -> {
                SectionType sectionType = SectionType.valueOf(o.getKey().name());
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) (o.getValue())).getContent());
                        break;

                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ListSection ls = (ListSection) o.getValue();
                        List<String> items = ls.getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;

                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection org = (OrganizationSection) o.getValue();
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
            });
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

            readWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
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
            });
            return resume;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void readWithException(DataInputStream dis, CustomReadInterface reader) throws IOException, IllegalAccessException, InstantiationException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumerInterface<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            consumer.accept(item);
        }
    }

    @FunctionalInterface
    interface CustomReadInterface {
        void read() throws IOException, InstantiationException, IllegalAccessException;
    }

    @FunctionalInterface
    interface CustomConsumerInterface<T> {
        void accept(T t) throws IOException;
    }
}