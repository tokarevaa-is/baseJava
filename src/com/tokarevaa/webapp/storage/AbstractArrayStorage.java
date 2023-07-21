package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doUpdate(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void doSave(Object searchKey, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            if ((int) searchKey < 0) {
                insertElement(r, (int) searchKey);
                size++;
            }
        }
    }

    @Override
    public void doDelete(Object searchKey) {
        fillDeletedElement((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, int index);
}