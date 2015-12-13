package com.nytimes.storedemo.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by 206847 on 12/13/15.
 */
@Singleton
public class ArticleApi {
    @Inject
    public ArticleApi() {
    }

    public Observable<String> getArticles() {
        return Observable.fromCallable(() -> "fake articles");
    }
}
