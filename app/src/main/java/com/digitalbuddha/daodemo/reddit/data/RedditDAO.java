package com.digitalbuddha.daodemo.reddit.data;


import com.digitalbuddha.daodemo.base.BaseDAO;
import com.digitalbuddha.daodemo.reddit.data.model.RedditData;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RedditDAO extends BaseDAO<RedditData> {

    @Inject
    public RedditDAO(RedditDaoLoader networkDAO) {
        super(networkDAO);
    }
}
