package com.nytimes.storedemo.store.article;

import com.nytimes.storedemo.model.ArticleEnvelope;
import com.nytimes.storedemo.rest.ArticleApi;
import com.nytimes.storedemo.store.base.NetworkDAO;
import com.nytimes.storedemo.util.Id;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by 206847 on 12/13/15.
 */
public class ArticleNetworkDAO implements NetworkDAO<String, ArticleEnvelope> {
    @Inject
    ArticleApi api;

    @Inject
    public ArticleNetworkDAO() {
    }

    @Override
    public Observable<String> fetch(Id<ArticleEnvelope> id) {
        return api.getArticles();
    }
}
