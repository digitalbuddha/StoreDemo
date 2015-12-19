package com.nytimes.storedemo.di;

import com.nytimes.storedemo.ui.MainView;
import com.nytimes.storedemo.ui.articlelist.ArticleRecyclerView;

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

    void inject(ArticleRecyclerView a);
}