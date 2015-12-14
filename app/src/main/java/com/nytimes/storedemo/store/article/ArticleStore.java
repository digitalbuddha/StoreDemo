package com.nytimes.storedemo.store.article;


import com.nytimes.storedemo.model.ArticleEnvelope;
import com.nytimes.storedemo.store.base.SubscriptionStore;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class ArticleStore extends SubscriptionStore<String, ArticleEnvelope> {
    @Inject
    public ArticleStore(ArticleParser parser, ArticleDiskDAO diskDAO, ArticleNetworkDAO networkDAO) {
        super(parser, diskDAO, networkDAO);

    }
}
