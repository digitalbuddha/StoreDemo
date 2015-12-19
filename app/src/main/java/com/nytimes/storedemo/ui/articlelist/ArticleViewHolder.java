package com.nytimes.storedemo.ui.articlelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nytimes.storedemo.R;
import com.nytimes.storedemo.model.Children;

/**
 * Created by brianplummer on 12/16/15.
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void onBind(Children article) {
        title.setText(article.data().title());
    }
}
