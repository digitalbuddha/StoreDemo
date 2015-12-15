package com.nytimes.storedemo.store.article;

import com.google.gson.Gson;
import com.nytimes.storedemo.model.ArticleEnvelope;
import com.nytimes.storedemo.model.ImmutableArticleEnvelope;
import com.nytimes.storedemo.rest.ArticleApi;
import com.nytimes.storedemo.store.base.NetworkDAO;
import com.nytimes.storedemo.util.Id;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by 206847 on 12/13/15.
 */
public class ArticleNetworkDAO implements NetworkDAO<ArticleEnvelope> {
    @Inject
    ArticleApi api;

    @Inject
    Gson gson;

    @Inject
    public ArticleNetworkDAO() {
    }

    @Override
    //will eventually go to retrofit & okhttp cache
    public Observable<ArticleEnvelope> fetch(Id<ArticleEnvelope> id) {
     return   Observable.fromCallable(() -> gson.fromJson(api.getArticles(), ImmutableArticleEnvelope.class));
    }
}
