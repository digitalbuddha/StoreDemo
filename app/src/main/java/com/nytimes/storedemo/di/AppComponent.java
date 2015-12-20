package com.nytimes.storedemo.di;

import com.nytimes.storedemo.DemoApplication;
import com.nytimes.storedemo.ui.MainView;
import com.nytimes.storedemo.ui.redditlist.PostViewHolder;
import com.nytimes.storedemo.ui.redditlist.RedditRecyclerView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component to be used by SampleActivity/Application
 */
@Singleton
@Component(
        modules = {ClientModule.class,StoreModule.class}
)
public interface AppComponent {
    void inject(MainView a);
    void inject(RedditRecyclerView a);
    void inject(DemoApplication demoApplication);
    void inject(PostViewHolder postViewHolder);
}