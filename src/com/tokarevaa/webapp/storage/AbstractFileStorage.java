package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        } else if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(File file, Resume resume);

    protected abstract Resume doRead(File file);

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Create file error", file.getName(), e);
        }

        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (Exception e) {
            throw new StorageException("Read file error", file.getName(), e);
        }
    }


    @Override
    protected void doDelete(File file) {
        try {
            if (!file.delete()) {
                throw new StorageException("Delete file error", file.getName());
            }
        } catch (Exception e) {
            throw new StorageException("Delete file error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (Exception e) {
            throw new StorageException("Update file error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        } else {
            throw new StorageException("Directory read error", directory.getPath());
        }
    }

    @Override
    public int size() {
        if (directory != null) {
            File[] files = directory.listFiles();
            if (files != null) {
                return files.length;
            } else {
                throw new StorageException("Directory read error", directory.getPath());
            }
        }
        return 0;
    }

    @Override
    public List<Resume> doGetAll() {
        ArrayList<Resume> resumes = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                resumes.add(doGet(file));
            }
        } else {
                throw new StorageException("Directory read error", directory.getPath());
        }
        return resumes;
    }
}