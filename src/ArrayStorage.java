/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;  // Количество резюме

    void clear() {
        for (int i = 0; i < size; i++)
            storage[i] = null;

        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid))
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {

            if (storage[i].uuid.equals(uuid)) {

                // Shift elements after deletion
                for (int j = i + 1; j < size; j++) {
                    storage[j - 1] = storage[j];
                }

                storage[size] = null;
                size--;

                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        System.arraycopy(storage, 0, allResume, 0, size);
        return allResume;
    }

    int size() {
        return size;
    }
}
