package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear();

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    void update(Resume resume);

    List<Resume> getAllSorted();

    int size();
}
