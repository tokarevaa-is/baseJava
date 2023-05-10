package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int resumeIndex = getIndex(r.getUuid());
        if (resumeIndex != -1) {
            storage[resumeIndex] = r;
            System.out.println("Resume " + r.getUuid() + " updated");
        } else {
            System.out.println("Resume " + r.getUuid() + " doesn't exist");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Resume storage overflow");
        } else {
            int insertIndex = getIndex(r.getUuid());
            if (insertIndex > 0) {
                System.out.println("Resume " + r.getUuid() + " is already exist");
            } else {
                insertElement(r, insertIndex);
                size++;
                System.out.println("Resume " + r.getUuid() + " added to storage");
            }
        }
    }

    protected abstract void insertElement(Resume r, int index);

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            fillDeletedElement(resumeIndex);
            storage[size] = null;
            size--;
            System.out.println("Resume " + uuid + " deleted");
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
    }

    protected abstract void fillDeletedElement(int resumeIndex);
}