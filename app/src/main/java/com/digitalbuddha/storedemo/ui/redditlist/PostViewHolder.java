package com.digitalbuddha.storedemo.ui.redditlist;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalbuddha.storedemo.DemoApplication;
import com.digitalbuddha.storedemo.model.Children;
import com.digitalbuddha.storedemo.model.Image;
import com.digitalbuddha.storedemo.util.DeviceUtils;
import com.nytimes.storedemo.R;
import com.squareup.picasso.Callback;
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

        if (screenWidth > screenHeight) {
            screenHeight = deviceUtils.getScreenWidth();
            screenWidth = deviceUtils.getScreenHeight();
        }

        maxHeight = (int) (screenHeight * .55f);
        margin = itemView.getContext().
                getResources()
                .getDimensionPixelSize(R.dimen.post_horizontal_margin);
        maxWidth = screenWidth - (2 * margin);
    }

    public void onBind(Children article) {
        title.setText(article.data().title());
        itemView.setBackground(new ColorDrawable(Color.WHITE));

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
                .into(thumbnail, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                        handleSuccess();
                    }
                    @Override
                    public void onError()
                    {itemView.setBackground(new ColorDrawable(Color.WHITE));}
                });
    }

    private void handleSuccess() {

        new AsyncTask<BitmapDrawable, Integer, Palette>(){
            @Override
            protected Palette doInBackground(BitmapDrawable... params)
            {
                Bitmap bitmap = params[0].getBitmap();
                Palette palette = Palette.from(bitmap).generate();
                return palette;
            }

            @Override
            protected void onPostExecute(Palette palette)
            {
                super.onPostExecute(palette);
                Palette.Swatch swatch = palette.getLightVibrantSwatch();
                if(swatch == null) {
                    swatch = palette.getVibrantSwatch();
                } else if(swatch == null) {
                    swatch = palette.getMutedSwatch();
                } else if(swatch == null) {
                    swatch = palette.getDarkVibrantSwatch();
                } else if(swatch == null) {
                    swatch = palette.getDarkMutedSwatch();
                }
                if(swatch!=null)
                {
                    title.setTextColor(swatch.getTitleTextColor());
                    title.setBackgroundColor(swatch.getRgb());
                    itemView.setBackground(new ColorDrawable(swatch.getRgb()));
                } else  {
                    itemView.setBackground(new ColorDrawable(Color.WHITE));
                }
            }
        }.execute((BitmapDrawable) thumbnail.getDrawable());
    }

}
