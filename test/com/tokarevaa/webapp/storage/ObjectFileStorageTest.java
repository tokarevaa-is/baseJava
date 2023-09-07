package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest{

    protected ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
