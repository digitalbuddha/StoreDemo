package com.digitalbuddha.daodemo.reddit.data;


import com.digitalbuddha.daodemo.base.DAO;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RedditDAO extends DAO<RedditData> {
    @Inject
    public RedditDAO() {
        super();
    }
}
