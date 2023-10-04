package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest{

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}
