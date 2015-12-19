package com.nytimes.storedemo.ui;

import com.nytimes.storedemo.model.Article;
import com.nytimes.storedemo.model.RedditData;
import com.nytimes.storedemo.store.article.ArticleStore;
import com.nytimes.storedemo.util.Id;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by 206847 on 12/13/15.
 */
public class MainPresenter implements Presenter<MainView> {
    public static final String FAKE_PARAM = "fakeKey";
    @Inject
    ArticleStore store;
    private MainView view;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void bind(MainView view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }

    public Observable<List<Article>> getArticles(){
        return store.get(Id.of(RedditData.class, FAKE_PARAM))
                .map(RedditData::articles);
    }
}
