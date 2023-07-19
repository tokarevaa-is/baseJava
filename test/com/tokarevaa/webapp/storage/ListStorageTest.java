package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.StorageException;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveOverflow() {
        throw new StorageException("Not for List class", "");
    }
}