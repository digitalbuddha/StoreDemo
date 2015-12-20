package com.digitalbuddha.daodemo.di;

import com.digitalbuddha.daodemo.dao.base.di.DAOModule;
import com.digitalbuddha.daodemo.ui.MainView;
import com.digitalbuddha.daodemo.ui.redditlist.PostViewHolder;
import com.digitalbuddha.daodemo.DemoApplication;
import com.digitalbuddha.daodemo.ui.redditlist.RedditRecyclerView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component to be used by SampleActivity/Application
 */
@Singleton
@Component(
        modules = {ClientModule.class,DAOModule.class}
)
public interface AppComponent {
    void inject(MainView a);
    void inject(RedditRecyclerView a);
    void inject(DemoApplication demoApplication);
    void inject(PostViewHolder postViewHolder);
}