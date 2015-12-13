package com.nytimes.storedemo.store.article;


import com.nytimes.storedemo.model.ArticleEnvelope;
import com.nytimes.storedemo.store.base.BaseStore;
import com.nytimes.storedemo.store.base.DiskDAO;
import com.nytimes.storedemo.store.base.NetworkDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Func1;

/**
 * Created by 206847 on 12/13/15.
 */
@Singleton
public class ArticleStore extends BaseStore<String, ArticleEnvelope> {
    @Inject
    ArticleParser parser;
    @Inject
    ArticleDiskDAO diskDAO;
    @Inject
    ArticleNetworkDAO networkDAO;

    @Inject
    public ArticleStore() {
    }

    @Override
    protected DiskDAO<String, ArticleEnvelope> getDiskDAO() {
        return diskDAO;
    }

    @Override
    protected NetworkDAO<String, ArticleEnvelope> getNetworkDAO() {
        return networkDAO;
    }

    @Override
    protected Func1<String, ArticleEnvelope> parser() {
        return parser;
    }
}
