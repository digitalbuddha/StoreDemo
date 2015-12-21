package com.digitalbuddha.daodemo.util;

import java.io.File;

/**
 * a Record represents metadata about a stored object, kind of like a java 'File'
 * or 'stat' in a unix filesystem. The idea here is to allow you to get info like
 * last modified time without actually reading the file's contents.
 */
public class Record<T> {

    private final Request<T> request;
    private final File file;

    public Record(Request<T> request, File file) {

        if (request == null) {
            throw new NullPointerException("need an Request to create a record");
        }
        if (file == null) {
            throw new NullPointerException("need a file to create a record");
        }

        this.request = request;
        this.file = file;
    }

    File getFile() {
        return file;
    }

    public Request<T> getRequest() {
        return request;
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
