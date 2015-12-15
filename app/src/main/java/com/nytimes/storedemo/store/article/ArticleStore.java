package com.nytimes.storedemo.store.article;


import com.nytimes.storedemo.model.ArticleEnvelope;
import com.nytimes.storedemo.store.base.DiskDAO;
import com.nytimes.storedemo.store.base.Store;
import com.nytimes.storedemo.util.Id;
import com.nytimes.storedemo.util.Record;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


@Singleton
public class ArticleStore extends Store<String, ArticleEnvelope> {

    private final DiskDAO diskDAO = new DiskDAO() {
        @Override
        public Observable getData(Id id) {
            return null;
        }

        @Override
        public Observable<Record> store(Id id, Object o) {
            return null;
        }
    };

    @Inject
    public ArticleStore(ArticleParser parser, ArticleDiskDAO diskDAO, ArticleNetworkDAO networkDAO) {
        super(new ArticleParser(), diskDAO, id -> Observable.just(""));
    }
}
