package com.nytimes.storedemo.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;

import com.jakewharton.rxbinding.view.RxView;
import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.R;

import javax.inject.Inject;

/**
 * TODO: document your custom view class.
 */
public class MainView extends CoordinatorLayout {
    @Inject
    MainPresenter presenter;
    public MainView(Context context) {
        this(context, null);
    }
    public MainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((DemoApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        RxView.clicks(fab)
                .flatMap(aVoid -> presenter.getArticles())
                .subscribe(articles -> {
            displayArticles();
        });

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void displayArticles() {
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.unbind();
        super.onDetachedFromWindow();
    }
}
