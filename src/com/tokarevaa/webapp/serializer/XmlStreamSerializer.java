package com.tokarevaa.webapp.serializer;

import com.tokarevaa.webapp.model.*;
import com.tokarevaa.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class,
                Organization.class,
                OrganizationSection.class,
                TextSection.class,
                ListSection.class,
                Organization.Position.class);
    }


    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
