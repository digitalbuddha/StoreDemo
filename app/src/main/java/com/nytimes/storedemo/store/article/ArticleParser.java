package com.nytimes.storedemo.store.article;

import com.google.gson.Gson;
import com.nytimes.storedemo.model.ArticleEnvelope;

import javax.inject.Inject;

import rx.functions.Func1;

/**
 * Created by 206847 on 12/13/15.
 */
public class ArticleParser implements Func1<String, ArticleEnvelope> {
    @Inject
    Gson gson;

    @Inject
    public ArticleParser() {
    }

    @Override
    public ArticleEnvelope call(String s) {
        return new Gson().fromJson(s, ArticleEnvelope.class);
    }
}
