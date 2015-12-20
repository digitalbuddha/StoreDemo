package com.nytimes.storedemo.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;

import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.R;
import com.nytimes.storedemo.model.Children;
import com.nytimes.storedemo.ui.redditlist.RedditRecyclerView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * TODO: document your custom view class.
 */
public class MainView extends CoordinatorLayout {
    @Inject
    MainPresenter presenter;
    @Inject
    OkHttpClient client;

    RedditRecyclerView redditRecyclerView;

    public MainView(Context context) {
        this(context, null);
    }

    public MainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                    try {
                        Iterator<String> urls = client.getCache().urls();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("presenter","error retreiving data");
                });
    }

    private void loadPosts(List<Children> posts) {
        // prefetch images....
        for(Children child : posts) {
            Picasso.with(getContext())
                    .load(child.data().preview().images().get(0).source().url())
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
