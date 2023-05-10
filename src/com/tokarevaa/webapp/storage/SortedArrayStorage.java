package com.tokarevaa.webapp.storage;

import com.tokarevaa.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int resumeInsertIndex = Math.abs(index) - 1;
        int numShiftedElements = size - resumeInsertIndex + 1;
        if (numShiftedElements > 0) {
            System.arraycopy(storage, resumeInsertIndex, storage, resumeInsertIndex + 1, numShiftedElements);
        }
        storage[resumeInsertIndex] = r;
    }

    @Override
    protected void fillDeletedElement(int resumeIndex) {
        int numShiftedElements = size - resumeIndex - 1;
        if (numShiftedElements > 0){
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, numShiftedElements);
        }
    }
}