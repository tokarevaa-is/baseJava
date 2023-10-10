package com.tokarevaa.webapp.serializer;

import java.io.IOException;

public interface CustomConsumerInterface<T> {
    void accept(T t) throws IOException;
}

