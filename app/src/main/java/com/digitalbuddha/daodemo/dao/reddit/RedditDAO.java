package com.digitalbuddha.daodemo.dao.reddit;


import com.digitalbuddha.daodemo.dao.base.BaseDAO;
import com.digitalbuddha.daodemo.model.RedditData;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RedditDAO extends BaseDAO<RedditData> {

    @Inject
    public RedditDAO(RedditDaoLoader networkDAO) {
        super(networkDAO);
    }
}
