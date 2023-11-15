package com.tokarevaa.webapp;

import com.tokarevaa.webapp.storage.SQLStorage;
import com.tokarevaa.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    protected static final File PROPERTIES = new File(
            "C:\\Users\\aatokarev.MSC\\Documents\\java\\baseJava\\basejava\\config\\resume.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storageSQL;

    private Config() {
        try (InputStream is = Files.newInputStream(PROPERTIES.toPath())) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storageSQL = new SQLStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorageSQL() {
        return storageSQL;
    }
}