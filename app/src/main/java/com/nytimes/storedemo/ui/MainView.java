package com.nytimes.storedemo.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;

import com.jakewharton.rxbinding.view.RxView;
import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.R;
import com.nytimes.storedemo.model.Children;
import com.nytimes.storedemo.ui.articlelist.ArticleRecyclerView;

import java.util.List;

import javax.inject.Inject;

/**
 * TODO: document your custom view class.
 */
public class MainView extends CoordinatorLayout {
    @Inject
    MainPresenter presenter;

    ArticleRecyclerView articleRecyclerView;

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
        articleRecyclerView = (ArticleRecyclerView) findViewById(R.id.articleRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        RxView.clicks(fab)
                .flatMap(aVoid -> presenter.getArticles())
                .subscribe(articles -> {
            displayArticles(articles);
        });
    }

    private void displayArticles(List<Children> articles) {
        articleRecyclerView.setArticles(articles);
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.unbind();
        super.onDetachedFromWindow();
    }
}
