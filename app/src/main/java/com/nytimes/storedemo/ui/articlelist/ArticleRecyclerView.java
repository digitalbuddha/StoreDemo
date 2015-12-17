package com.nytimes.storedemo.ui.articlelist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.model.Article;
import com.nytimes.storedemo.ui.articlelist.ArticleAdapter;
import com.nytimes.storedemo.ui.articlelist.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by brianplummer on 12/16/15.
 */
public class ArticleRecyclerView extends RecyclerView {

    @Inject
    protected ArticleAdapter articleAdapter;

    public ArticleRecyclerView(Context context) {
        this(context, null);
    }

    public ArticleRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArticleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
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
        setAdapter(articleAdapter);
    }

    public void notifyDataSetChanged() {
        articleAdapter.notifyDataSetChanged();
    }

    public void setArticles(List<Article> articles) {
        articleAdapter.setArticles(articles);
    }
}
