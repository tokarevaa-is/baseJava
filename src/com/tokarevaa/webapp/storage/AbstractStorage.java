package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getResumeByKey(String uuid);

    protected abstract void doSave(Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    @Override
    public void save(Resume resume) {
        getNotExistingSearchKey(resume.getUuid());
        doSave(resume);
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

    private void getNotExistingSearchKey(String uuid) {
        Object searchKey = getResumeByKey(uuid);
        if (searchKey != null) {
            throw new ExistStorageException(uuid);
        }
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getResumeByKey(uuid);
        if (searchKey == null) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
