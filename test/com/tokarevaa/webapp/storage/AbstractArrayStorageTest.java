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
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final String NOTEXISTS = "NotExist";
    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
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
        assertGet(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("unknown");
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[3];

        resumes[0] = RESUME_1;
        resumes[1] = RESUME_2;
        resumes[2] = RESUME_3;

        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NOTEXISTS);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = storage.size(); i <= AbstractArrayStorage.STORAGE_LIMIT - 1; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Overflow raised earlier");
        }

        storage.save(new Resume());
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}