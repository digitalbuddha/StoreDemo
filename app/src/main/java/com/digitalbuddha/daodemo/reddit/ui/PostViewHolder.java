package com.digitalbuddha.daodemo.reddit.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalbuddha.daodemo.reddit.data.model.Children;
import com.digitalbuddha.daodemo.reddit.data.model.Image;
import com.digitalbuddha.daodemo.util.BitmapTransform;
import com.digitalbuddha.daodemo.util.DeviceUtils;
import com.digitalbuddha.daodemo.DemoApplication;
import com.nytimes.storedemo.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by brianplummer on 12/16/15.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    @Inject
    DeviceUtils deviceUtils;
    private int maxHeight;
    private int maxWidth;
    private TextView title;
    private ImageView thumbnail;
    private View topSpacer;

    public PostViewHolder(View itemView) {
        super(itemView);
        performInjection(itemView);
        findViews(itemView);
        setMaxDimensions(itemView);
    }

    public void onBind(Children article) {
        title.setText(article.data().title());
        if (article.data().preview().isPresent()) {
            showImage(article);
        }
    }

    private void showImage(Children article) {
        Image image = article.data().preview().get().images().get(0).source();
        BitmapTransform bitmapTransform = new BitmapTransform(maxWidth, maxHeight, image);

        int targetWidth = bitmapTransform.targetWidth;
        int targetHeight = bitmapTransform.targetHeight;

        setSpacer(targetWidth, targetHeight);

        setupThumbnail(targetWidth, targetHeight);

        Picasso.with(itemView.getContext())
                .load(image.url())
                .transform(bitmapTransform)
                .resize(targetWidth, targetHeight)
                .centerInside()
                .placeholder(R.color.gray80)
                .into(thumbnail);
    }

    private void setSpacer(int targetWidth, int targetHeight) {
        if (targetWidth >= targetHeight) {
            topSpacer.setVisibility(View.GONE);
        } else {
            topSpacer.setVisibility(View.VISIBLE);
        }
    }

    private void setupThumbnail(int targetWidth, int targetHeight) {
        thumbnail.setMaxWidth(targetWidth);
        thumbnail.setMaxHeight(targetHeight);
        thumbnail.setMinimumWidth(targetWidth);
        thumbnail.setMinimumHeight(targetHeight);
        thumbnail.requestLayout();
    }

    private void setMaxDimensions(View itemView) {
        int screenWidth;
        int screenHeight;
        screenWidth = deviceUtils.getScreenWidth();
        screenHeight = deviceUtils.getScreenHeight();

        if (screenWidth > screenHeight) {
            screenHeight = deviceUtils.getScreenWidth();
            screenWidth = deviceUtils.getScreenHeight();
        }

        maxHeight = (int) (screenHeight * .55f);
        int margin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.post_horizontal_margin);
        maxWidth = screenWidth - (2 * margin);
    }

    private void findViews(View itemView) {
        title = (TextView) itemView.findViewById(R.id.title);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        topSpacer = itemView.findViewById(R.id.topSpacer);
    }

    private void performInjection(View itemView) {
        ((DemoApplication) itemView.getContext()
                .getApplicationContext())
                .getApplicationComponent()
                .inject(this);
    }
}
