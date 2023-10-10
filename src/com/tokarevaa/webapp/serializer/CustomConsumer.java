package com.tokarevaa.webapp.serializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

public class CustomConsumer implements CustomConsumerInterface {

    public static <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumerInterface<T> consumer) throws Exception {
        dos.writeInt(collection.size());
        for (T item : collection) {
            consumer.accept(item);
        }
    }

    @Override
    public void accept(Object o) throws IOException {
    }
}
