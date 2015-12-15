package com.nytimes.storedemo.store.base;

import com.nytimes.storedemo.util.Id;
import com.nytimes.storedemo.util.Record;

import rx.Observable;

/**
 * Created by 206847 on 12/14/15.
 */
public class DiskSplitDAO<Raw, Parsed> implements DiskDAO<Raw, Parsed> {

    private final DiskLoader<Raw, Parsed> diskLoader;
    private final DiskSaver<Raw, Parsed> diskSaver;

    private DiskSplitDAO(DiskLoader<Raw, Parsed> diskLoader,
                         DiskSaver<Raw, Parsed> diskSaver) {
        this.diskLoader = diskLoader;
        this.diskSaver = diskSaver;
    }

    public static <Raw, Parsed> DiskSplitDAO<Raw, Parsed> create(DiskLoader<Raw, Parsed> diskLoader,
                                                                 DiskSaver<Raw, Parsed> diskSaver) {
        return new DiskSplitDAO<>(diskLoader, diskSaver);
    }


    @Override
    public Observable<Raw> getData(Id<Parsed> id) {
        return diskLoader.getData(id);
    }

    @Override
    public Observable<Record<Parsed>> store(Id<Parsed> id, Raw raw) {
        return diskSaver.store(id, raw);
    }
}
