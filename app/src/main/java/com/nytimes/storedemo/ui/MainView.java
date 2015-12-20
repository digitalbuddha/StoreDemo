package com.nytimes.storedemo.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.R;
import com.nytimes.storedemo.ui.redditlist.RedditRecyclerView;

import javax.inject.Inject;

/**
 * TODO: document your custom view class.
 */
public class MainView extends CoordinatorLayout {
    @Inject
    MainPresenter presenter;

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
                .subscribe(redditRecyclerView::setArticles);
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.unbind();
        super.onDetachedFromWindow();
    }
}
