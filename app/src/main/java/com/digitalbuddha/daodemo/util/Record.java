package com.digitalbuddha.daodemo.util;

import java.io.File;

/**
 * a Record represents metadata about a stored object, kind of like a java 'File'
 * or 'stat' in a unix filesystem. The idea here is to allow you to get info like
 * last modified time without actually reading the file's contents.
 */
public class Record<T> {

    private final Id<T> id;
    private final File file;

    public Record(Id<T> id, File file) {

        if (id == null) {
            throw new NullPointerException("need an Id to create a record");
        }
        if (file == null) {
            throw new NullPointerException("need a file to create a record");
        }

        this.id = id;
        this.file = file;
    }

    File getFile() {
        return file;
    }

    public Id<T> getId() {
        return id;
    }

    public boolean exists() {
        return file.exists();
    }

    public long getLastModified() {
        return file.lastModified();
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

}
