package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.NotExistStorageException;
import com.tokarevaa.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<KeyValue> implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);
    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract KeyValue getSearchKey(String uuid);

    protected abstract void doSave(KeyValue searchKey, Resume resume);

    protected abstract Resume doGet(KeyValue searchKey);

    protected abstract void doDelete(KeyValue searchKey);

    protected abstract void doUpdate(KeyValue searchKey, Resume resume);

    protected abstract boolean isExist(KeyValue searchKey);

    protected abstract List<Resume> doGetAll();

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    private KeyValue getNotExistingSearchKey(String uuid) {
        KeyValue searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private KeyValue getExistingSearchKey(String uuid) {
        KeyValue searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
