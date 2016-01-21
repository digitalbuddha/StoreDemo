package com.digitalbuddha.daodemo.reddit.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;

import com.digitalbuddha.daodemo.DemoApplication;
import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.nytimes.storedemo.R;

import java.util.List;

import javax.inject.Inject;


public class RedditView extends CoordinatorLayout {
    @Inject
    RedditPresenter presenter;

    RedditRecyclerView redditRecyclerView;

    public RedditView(Context context) {
        this(context, null);
    }

    public RedditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((DemoApplication) context.getApplicationContext())
                .getApplicationComponent().inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.bind(this);
        redditRecyclerView = (RedditRecyclerView) findViewById(R.id.postRecyclerView);

        presenter.getPosts()
                .subscribe(this::showPosts, throwable -> {
                    Log.d("presenter",throwable.toString());
                });
    }

    private void showPosts(List<Children> posts) {
        redditRecyclerView.postAdapter.setArticles(posts);
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.unbind();
        super.onDetachedFromWindow();
    }
}
