package com.digitalbuddha.daodemo.reddit.ui;

import com.digitalbuddha.daodemo.reddit.data.RedditDAO;
import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;
import com.digitalbuddha.daodemo.util.Id;
import com.digitalbuddha.daodemo.util.Presenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RedditPresenter implements Presenter<RedditView> {
    public static final String LIMIT = "100";
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
//
    public Observable<List<Children>> getPosts(){
        return store.get(Id.of(RedditData.class, LIMIT))
               .doOnNext(this::prefetchImages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(redditData -> redditData.data().children());
    }

    private void prefetchImages(RedditData redditData) {
        for(Children child : redditData.data().children()) {
            if(child.data().preview().isPresent())
                Picasso.with(view.getContext())
                        .load(child.data().preview().get().images().get(0).source().url())
                        .fetch();
        }
    }
}
