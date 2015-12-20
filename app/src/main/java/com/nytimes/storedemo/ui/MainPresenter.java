package com.nytimes.storedemo.ui;

import com.nytimes.storedemo.model.Children;
import com.nytimes.storedemo.model.RedditData;

import com.nytimes.storedemo.store.reddit.RedditStore;
import com.nytimes.storedemo.util.Id;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public Observable<List<Children>> getPosts(){
        store.clearMemory();
        return store.get(Id.of(RedditData.class, FAKE_PARAM))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(redditData -> redditData.data().children());
    }
}
