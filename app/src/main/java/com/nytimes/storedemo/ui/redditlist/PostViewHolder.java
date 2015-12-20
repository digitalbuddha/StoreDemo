package com.nytimes.storedemo.ui.redditlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.R;
import com.nytimes.storedemo.model.Children;
import com.nytimes.storedemo.model.Image;
import com.nytimes.storedemo.util.DeviceUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by brianplummer on 12/16/15.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    @Inject
    protected DeviceUtils deviceUtils;
    private int maxHeight, margin, maxWidth;
    private TextView title;
    private ImageView thumbnail;
    private View topSpacer;

    public PostViewHolder(View itemView) {
        super(itemView);
        int screenWidth, screenHeight;
        ((DemoApplication)itemView.getContext()
                .getApplicationContext())
                .getApplicationComponent()
                .inject(this);
        title = (TextView) itemView.findViewById(R.id.title);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        topSpacer = itemView.findViewById(R.id.topSpacer);
        screenWidth = deviceUtils.getScreenWidth();
        screenHeight = deviceUtils.getScreenHeight();
        maxHeight = (int) (screenHeight * .55f);
        margin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.post_horizontal_margin);
        maxWidth = screenWidth - (2 * margin);
    }

    public void onBind(Children article) {
        title.setText(article.data().title());

        Image image = article.data().preview().images().get(0).source();
        BitmapTransform bitmapTransform = new BitmapTransform(maxWidth, maxHeight, image);

        int targetWidth = bitmapTransform.targetWidth;
        int targetHeight = bitmapTransform.targetHeight;

        if (targetWidth >= targetHeight) {
            topSpacer.setVisibility(View.GONE);
        } else {
            topSpacer.setVisibility(View.VISIBLE);
        }

        thumbnail.setMaxWidth(targetWidth);
        thumbnail.setMaxHeight(targetHeight);
        thumbnail.setMinimumWidth(targetWidth);
        thumbnail.setMinimumHeight(targetHeight);
        thumbnail.requestLayout();

        Picasso.with(itemView.getContext())
                .load(image.url())
                .transform(bitmapTransform)
                .resize(targetWidth, targetHeight)
                .centerInside()
                .placeholder(R.color.gray80)
                .into(thumbnail);
    }
}
