package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected int getResumeByKey(String uuid) {
        for (ListIterator<Resume> iterator = list.listIterator(); iterator.hasNext(); ) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)){
                return iterator.nextIndex() - 1;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(int index) {
        return list.get(index);
    }

    @Override
    protected void doDelete(Resume resume) {
        list.remove(resume);
    }

    @Override
    protected void doUpdate(int index, Resume resume) {
        list.set(index, resume);
    }
}
