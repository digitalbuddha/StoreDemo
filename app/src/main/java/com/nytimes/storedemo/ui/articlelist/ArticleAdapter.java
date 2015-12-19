package com.nytimes.storedemo.ui.articlelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nytimes.storedemo.R;
import com.nytimes.storedemo.model.Children;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by brianplummer on 12/16/15.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<Children> articles = new ArrayList<>();


    @Inject
    public ArticleAdapter () {}

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.onBind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Children> articlesToAdd) {
        articles.clear();
        articles.addAll(articlesToAdd);
        notifyDataSetChanged();
    }
}
