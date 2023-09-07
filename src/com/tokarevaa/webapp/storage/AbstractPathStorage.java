package com.tokarevaa.webapp.storage;

import com.sun.xml.internal.txw2.output.StreamSerializer;
import com.tokarevaa.webapp.exception.StorageException;
import com.tokarevaa.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private StreamSerializer streamSerializer;

    public AbstractPathStorage(String dir, StreamSerializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory must not be null");
        streamSerializer = serializer;

        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
    }

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Create file error", getFileName(path), e);
        }

        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Read file error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new StorageException("Delete file error", getFileName(path), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (Exception e) {
            throw new StorageException("Update file error", getFileName(path), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    public List<Resume> doGetAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}