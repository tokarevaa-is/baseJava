package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = storage.size(); i <= AbstractArrayStorage.STORAGE_LIMIT - 1; i++) {
                storage.save(new Resume("Name " + i));
            }
        } catch (StorageException e) {
            Assert.fail("Overflow raised earlier");
        }

        storage.save(new Resume("New Name"));
    }
}
