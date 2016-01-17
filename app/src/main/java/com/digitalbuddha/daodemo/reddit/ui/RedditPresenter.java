package com.digitalbuddha.daodemo.reddit.ui;

import com.digitalbuddha.daodemo.reddit.data.RedditApi;
import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.digitalbuddha.daodemo.reddit.data.model.Data;
import com.digitalbuddha.daodemo.util.Id;
import com.digitalbuddha.daodemo.reddit.data.RedditDAO;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;
import com.digitalbuddha.daodemo.util.Presenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RedditPresenter implements Presenter<RedditView> {
    public static final String LIMIT = "20";
    @Inject
    RedditDAO store;

    private RedditView view;

    @Inject
    public RedditPresenter() {
    }

    @Override
    public void bind(RedditView view) {
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
