package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected Object getResumeByKey(String uuid) {
        Resume resume = doGet(uuid);
        if (resume != null) {
            return resume.getUuid();
        }
        return null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return resumeMap.get((String) searchKey);
    }

    @Override
    protected void doSave(Resume resume) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        resumeMap.remove((String) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        resumeMap.put((String) searchKey, resume);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
