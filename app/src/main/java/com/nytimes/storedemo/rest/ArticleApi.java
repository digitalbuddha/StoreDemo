package com.nytimes.storedemo.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

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
        URL url = Resources.getResource("src/main/resources/articles.json");
        try {
            String text = Resources.toString(url, Charsets.UTF_8);
            return Observable.fromCallable(() -> text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
