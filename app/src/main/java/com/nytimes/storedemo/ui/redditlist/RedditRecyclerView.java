package com.nytimes.storedemo.ui.redditlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.model.Children;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by brianplummer on 12/16/15.
 */
public class RedditRecyclerView extends RecyclerView {

    @Inject
    protected PostAdapter postAdapter;

    public RedditRecyclerView(Context context) {
        this(context, null);
    }

    public RedditRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedditRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ((DemoApplication) (context).getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        setLayoutManager(layoutManager);
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        setAdapter(postAdapter);
    }

    public void notifyDataSetChanged() {
        postAdapter.notifyDataSetChanged();
    }

    public void setArticles(List<Children> articles) {
        postAdapter.setArticles(articles);
    }
}
