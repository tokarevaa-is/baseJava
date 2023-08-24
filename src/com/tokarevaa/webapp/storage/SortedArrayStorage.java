package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, (Comparator.comparing(Resume::getUuid)
        ));
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int insertionIndex = Math.abs(index) - 1;
        int countMoved = size - insertionIndex;
        if (countMoved > 0) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, countMoved);
        }
        storage[insertionIndex] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int countMoved = size - index - 1;
        if (countMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, countMoved);
        }
    }
}