package com.tokarevaa.webapp.storage;

import org.junit.Assert;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() {
        Assert.assertArrayEquals(super.getStaticResumes(), Arrays.stream(storage.getAll()).sorted().toArray());
    }
}