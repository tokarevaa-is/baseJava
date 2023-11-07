package com.tokarevaa.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ListStorageTest.class,
        MapResumeStorageTest.class,
        MapStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        SortedStorageTest.class,
        StorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        DataPathStorageTest.class,
        SQLStorageTest.class
})

public class AllTests {
}