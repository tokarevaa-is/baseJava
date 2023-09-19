package com.tokarevaa.webapp.serializer;

import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.ContactType;
import com.tokarevaa.webapp.model.Resume;
import com.tokarevaa.webapp.model.Section;
import com.tokarevaa.webapp.model.SectionType;

import java.io.*;
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
                if (sections.get(st) != null)   //$$$
                    dos.writeUTF(sections.get(st).toGson());
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
                try {
                    section = st.getSection().newInstance();
                    if (section != null) {
                        section.parseJson(dis.readUTF());
                        resume.setSection(st, section);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new StorageException("Error, while parsing JSON", null, e);
                } catch (EOFException e) {
                    break;
                }
            }
            return resume;
        }
    }
}