package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    protected final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    // Resume Count
    int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT)
            // Overflow check
            System.out.println("Resume storage overflow");
        else if (getIndex(r.uuid) != -1)
            // Existence check
            System.out.println("Resume " + r.uuid + " is already exist");
        else {
            // Add new resume
            storage[size] = r;
            size++;
            System.out.println("Resume " + r.uuid + " added to storage");
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            return storage[resumeIndex];
        }
        System.out.println("Resume " + uuid + " doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            storage[resumeIndex] = storage[size];
            storage[size] = null;
            size--;
            System.out.println("Resume " + uuid + " deleted");
        } else
            System.out.println("Resume " + uuid + " doesn't exist");
    }

    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.uuid);
        if (resumeIndex != -1) {
            storage[resumeIndex] = resume;
            System.out.println("Resume " + resume.uuid + " updated");
        } else
            System.out.println("Resume " + resume.uuid + " doesn't exist");
    }

    //Check existence of Resume in Storage
    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid))
                return i;
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
