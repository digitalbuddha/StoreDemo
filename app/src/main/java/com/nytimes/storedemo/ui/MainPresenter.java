package com.nytimes.storedemo.ui;

import com.nytimes.storedemo.model.RedditData;

<<<<<<< 8e48d68a88888d52d8ada04773350f163805641d
import com.nytimes.storedemo.model.Article;
import com.nytimes.storedemo.store.article.RedditStore;
=======
import com.nytimes.storedemo.model.RedditData;
import com.nytimes.storedemo.store.article.ArticleStore;
import com.nytimes.storedemo.util.Id;
>>>>>>> start of data model

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

    public Observable<List<RedditData>> getArticles(){
        return store.get(Id.of(RedditData.class, FAKE_PARAM))
                .map(redditData -> redditData.data().children());
    }
}
