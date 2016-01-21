package com.digitalbuddha.daodemo.di;

import com.digitalbuddha.daodemo.base.di.DAOModule;
import com.digitalbuddha.daodemo.reddit.ui.RedditView;
import com.digitalbuddha.daodemo.reddit.ui.PostViewHolder;
import com.digitalbuddha.daodemo.DemoApplication;
import com.digitalbuddha.daodemo.reddit.ui.RedditRecyclerView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component to be used by SampleActivity/Application
 */
@Singleton
@Component(
        modules = {RedditModule.class,DAOModule.class}
)
public interface AppComponent {
    void inject(RedditView a);
    void inject(RedditRecyclerView a);
    void inject(DemoApplication demoApplication);
    void inject(PostViewHolder postViewHolder);
}