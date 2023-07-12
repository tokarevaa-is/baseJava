package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int getResumeByKey(String uuid);

    protected abstract void doSave(Resume resume);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(Resume uuid);

    protected abstract void doUpdate(int index, Resume resume);

    @Override
    public void save(Resume resume) {
        if (isExist(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(resume);
    }

    private boolean isExist(String uuid) {
        return getResumeByKey(uuid) >= 0;
    }

    @Override
    public Resume get(String uuid) {
        int index = getResumeByKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(index);
    }

    @Override
    public void delete(String uuid) {
        Resume resume = get(uuid);
        if (resume != null) {
            doDelete(resume);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getResumeByKey(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(index, resume);
    }
}
