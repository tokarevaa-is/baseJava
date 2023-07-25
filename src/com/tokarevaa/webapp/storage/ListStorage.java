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
    public int size() {
        return list.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (ListIterator<Resume> iterator = list.listIterator(); iterator.hasNext(); )  {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return iterator.nextIndex() - 1;
            }
        }
        return null;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        if (searchKey == null) {
            return false;
        }
        return doGet(searchKey) != null;
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(list);
    }
}
