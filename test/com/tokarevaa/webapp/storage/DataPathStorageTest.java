package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest{

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
