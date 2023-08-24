package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    public void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    private Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public List<Resume> doGetAll() {
        return Arrays.asList(getAll());
    }

    @Override
    public void doSave(Integer searchKey, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            if (searchKey < 0) {
                insertElement(r, searchKey);
                size++;
            }
        }
    }

    @Override
    public void doDelete(Integer searchKey) {
        fillDeletedElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, int index);
}