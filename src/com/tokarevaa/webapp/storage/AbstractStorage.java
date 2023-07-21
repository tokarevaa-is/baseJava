package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract boolean isExist(Object searchKey);

    @Override
    public void save(Resume resume) {
        doSave(getNotExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        doDelete(getExistingSearchKey(uuid));
    }

    @Override
    public void update(Resume resume) {
        doUpdate(getExistingSearchKey(resume.getUuid()), resume);
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
