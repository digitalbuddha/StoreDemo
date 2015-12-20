package com.digitalbuddha.storedemo.ui;

import com.digitalbuddha.storedemo.model.Children;
import com.digitalbuddha.storedemo.store.reddit.RedditStore;
import com.digitalbuddha.storedemo.util.Id;
import com.digitalbuddha.storedemo.model.RedditData;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 206847 on 12/13/15.
 */
public class MainPresenter implements Presenter<MainView> {
    public static final String LIMIT = "20";
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
        return store.get(Id.of(RedditData.class, LIMIT))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(redditData -> redditData.data().children());
    }
}
