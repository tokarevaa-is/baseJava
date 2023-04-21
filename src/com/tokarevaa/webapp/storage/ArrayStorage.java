package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;  // Количество резюме

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        // Existence check
        if (isExist(r.uuid)) {
            System.out.println("Resume " + r.uuid + " is exist");
            return;
        }

        // Overflow check
        if ((size + 1) >= storage.length) {
            System.out.println("Resume storage overflow");
            return;
        }

        // Add new resume
        Resume newResume = new Resume();
        newResume.uuid = r.uuid;
        storage[size] = newResume;
        size++;

    }

    public Resume get(String uuid) {
        if (isExist(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid))
                    return storage[i];
            }
        } else
            System.out.println("Resume " + uuid + " doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        if (isExist(uuid)) {
            for (int i = 0; i < size; i++) {

                if (storage[i].uuid.equals(uuid)) {

                    // Shift elements after deletion
                    for (int j = i + 1; j < size; j++) {
                        storage[j - 1] = storage[j];
                    }

                    storage[size] = null;
                    size--;

                    break;
                }
            }
        } else
            System.out.println("Resume " + uuid + " doesn't exist");
    }

    public void update(Resume resume) {
        if (isExist(resume.uuid)) {
            Resume storageResume = get(resume.uuid);
            if (storageResume != null) {
                storageResume.uuid = resume.uuid;
                System.out.println("Resume " + resume.uuid + " updated");
            }
        } else
            System.out.println("Resume " + resume.uuid + " doesn't exist");
    }

    //Check existence of Resume in Storage
    private boolean isExist(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid))
                return true;
        return false;
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
