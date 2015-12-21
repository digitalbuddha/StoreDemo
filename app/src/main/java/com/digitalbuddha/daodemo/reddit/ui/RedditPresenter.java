package com.digitalbuddha.daodemo.reddit.ui;

import com.digitalbuddha.daodemo.reddit.data.Reddit;
import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.digitalbuddha.daodemo.util.Request;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;
import com.digitalbuddha.daodemo.util.Presenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 206847 on 12/13/15.
 */
public class RedditPresenter implements Presenter<RedditView> {
    public static final String LIMIT = "20";


    @Inject
    Reddit reddit;
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
        Observable<RedditData> aww = reddit.aww(LIMIT);
        Request<RedditData> request = Request.of(RedditData.class, Reddit.getUrl(), aww);
        return reddit.run().fresh(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(redditData -> redditData.data().children());
    }
}
