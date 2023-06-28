package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    public static final int STORAGE_LIMIT = 10_000;

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
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
            System.out.println("Resume " + r.getUuid() + " updated");
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            int index = getIndex(r.getUuid());
            if (index < 0) {
                insertElement(r, index);
                size++;
                System.out.println("Resume " + r.getUuid() + " added to storage");
            } else {
                throw new ExistStorageException(r.getUuid());
            }
        }
    }

    protected abstract void insertElement(Resume r, int index);

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
            System.out.println("Resume " + uuid + " deleted");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void fillDeletedElement(int index);
}