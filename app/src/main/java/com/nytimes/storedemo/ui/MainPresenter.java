package com.nytimes.storedemo.ui;

import com.nytimes.storedemo.model.Article;
import com.nytimes.storedemo.store.article.RedditStore;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by 206847 on 12/13/15.
 */
public class MainPresenter implements Presenter<MainView> {
    public static final String FAKE_PARAM = "fakeKey";
    @Inject
    RedditStore store;
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
        return null;
    }
}
