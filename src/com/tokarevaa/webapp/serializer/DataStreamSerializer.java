package com.tokarevaa.webapp.serializer;

import com.tokarevaa.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            for (SectionType st : SectionType.values()) {
                if (sections.get(st) != null) {
                    if (sections.get(st).getClass() == TextSection.class) {
                        TextSection ts = (TextSection) sections.get(st);
                        dos.writeUTF(ts.toString());

                    } else if (sections.get(st).getClass() == ListSection.class) {
                        ListSection ls = (ListSection) sections.get(st);
                        List<String> items = ls.getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }

                    } else if (sections.get(st).getClass() == OrganizationSection.class) {
                        OrganizationSection org = (OrganizationSection) sections.get(st);
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
                    }
                }
            }
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

            for (SectionType st : SectionType.values()) {

                Section section = null;

                if (st.getSection() == TextSection.class) {
                    section = new TextSection(dis.readUTF());

                } else if (st.getSection() == ListSection.class) {
                    section = new ListSection();
                    int count = dis.readInt();
                    while (count > 0) {
                        ((ListSection) section).add(dis.readUTF());
                        count--;
                    }

                } else if (st.getSection() == OrganizationSection.class) {
                    section = new OrganizationSection();

                    int count = dis.readInt();
                    while (count > 0) {
                        List<Organization.Position> positions = new ArrayList<>();
                        Organization organization = new Organization(dis.readUTF(), dis.readUTF(), positions);

                        int positionCount = dis.readInt();
                        while (positionCount > 0) {
                            positions.add(new Organization.Position(dis.readUTF(), dis.readUTF(), LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF())));
                            positionCount--;
                        }

                        ((OrganizationSection) section).add(organization);
                        count--;
                    }
                }
                resume.setSection(st, section);
            }
            return resume;
        }
    }
}