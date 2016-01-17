package com.digitalbuddha.daodemo.reddit.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;

import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.digitalbuddha.daodemo.DemoApplication;
import com.nytimes.storedemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

/**
 * TODO: document your custom view class.
 */
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
                .subscribe(this::loadPosts, throwable -> {
                    Log.d("presenter","error retreiving data");
                });
    }

    private void loadPosts(List<Children> posts) {
        // prefetch images....
        for(Children child : posts) {
            if(child.data().preview().isPresent())
            Picasso.with(getContext())
                    .load(child.data().preview().get().images().get(0).source().url())
                    .fetch();
        }
        redditRecyclerView.setArticles(posts);
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.unbind();
        super.onDetachedFromWindow();
    }
}
