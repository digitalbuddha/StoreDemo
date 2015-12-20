package com.nytimes.storedemo.ui.redditlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nytimes.storedemo.R;
import com.nytimes.storedemo.model.Children;
import com.squareup.picasso.Picasso;

/**
 * Created by brianplummer on 12/16/15.
 */
public class PostVIewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private ImageView thumbnail;

    public PostVIewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    }

    public void onBind(Children article) {
        title.setText(article.data().title());
        Picasso.with(itemView.getContext())
                .load(article.data().thumbnail())
                .into(thumbnail);
    }
}
