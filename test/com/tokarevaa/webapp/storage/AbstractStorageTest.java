package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.Config;
import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.tokarevaa.webapp.model.ResumeTestData.fillNewResume;

public abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.get().getStorageDir();

    /*
    LITE_MODE:
    0 - only basic resume data (uuid, full name)
    1 - basic data + contacts
    2 - full data
    */
    private static final int LITE_MODE = 2;
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final Resume RESUME_1 = fillNewResume(UUID_1, "Name1", LITE_MODE);
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final Resume RESUME_2 = fillNewResume(UUID_2, "Name2", LITE_MODE);
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final Resume RESUME_3 = fillNewResume(UUID_3, "Name3", LITE_MODE);
    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final Resume RESUME_4 = fillNewResume(UUID_4, "Name4", LITE_MODE);
    private static final String UUID_NOT_EXISTS = "NotExist";
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    public Resume[] getStaticResumes() {
        return new Resume[]{RESUME_1, RESUME_2, RESUME_3};
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
        Resume newResume = fillNewResume(UUID_1, "Name Update", LITE_MODE);
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = fillNewResume(UUID_NOT_EXISTS, "Not Exist", LITE_MODE);
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_3));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXISTS);
    }

    @Test
    public void getAllSorted() {
        List<Resume> sortedResumes = Arrays.asList(getStaticResumes());
        Collections.sort(sortedResumes);
        Assert.assertEquals(sortedResumes, storage.getAllSorted());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXISTS);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}