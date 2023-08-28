package com.tokarevaa.webapp;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File directory = new File("../");
        goDeep(directory.getPath(), 0);
    }

    private static void goDeep(String path, int level) {
        File directory = new File(path);

        System.out.printf("%s├┬ %s\n", String.join("", Collections.nCopies(level, "│")), directory.getName());

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                goDeep(file.getPath(), level + 1);
            } else if (file.isFile()) {
                System.out.printf("%s│├─  %s\n", String.join("", Collections.nCopies(level, "│")), file.getName());
            }
        }
    }
}