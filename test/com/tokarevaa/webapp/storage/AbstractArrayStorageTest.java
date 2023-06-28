package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        checkSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        checkSize(0);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3);
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = new Resume("unexist");
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_3));
    }

    @Test
    public void get() {
        Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("unknown");
    }

    @Test
    public void getAll() {
        Resume[] Resumes = storage.getAll();
        Assert.assertEquals(UUID_1, Resumes[0].getUuid());
        Assert.assertEquals(UUID_2, Resumes[1].getUuid());
        Assert.assertEquals(UUID_3, Resumes[2].getUuid());

        checkSize(3);
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        checkSize(4);
        Assert.assertEquals(UUID_4, storage.get(UUID_4).getUuid());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        try {
            storage.get(UUID_1);
        } catch (NotExistStorageException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("notExist");
    }

    @Test
    public void overflowCheck() {
        try {
            for (int i = storage.size(); i <= AbstractArrayStorage.STORAGE_LIMIT - 1; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Overflow raised earlier");
        }

        try {
            storage.save(new Resume());
        } catch (StorageException e) {
            Assert.assertTrue(true);
        }
    }

    private void checkSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}