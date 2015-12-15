package com.nytimes.storedemo.store.article;

import com.nytimes.storedemo.model.ArticleEnvelope;
import com.nytimes.storedemo.store.base.DiskDAO;
import com.nytimes.storedemo.util.Id;
import com.nytimes.storedemo.util.Record;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by 206847 on 12/13/15.
 */
@Singleton
public class ArticleDiskDAO implements DiskDAO<String, ArticleEnvelope> {

    //This would normally be an implementation of SQL Lite or File Storage
    private static String fakeDiskStorage;

    @Inject
    public ArticleDiskDAO() {
    }

    @Override
    public Observable<String> getData(Id<ArticleEnvelope> id) {
        return Observable.fromCallable(() -> fakeDiskStorage);
    }

    @Override
    public Observable<Record<ArticleEnvelope>> store(final Id<ArticleEnvelope> id, String s) {
        fakeDiskStorage = s;
        return Observable.fromCallable(() -> new Record<>(id, new File("fakeFile")));
    }
}
