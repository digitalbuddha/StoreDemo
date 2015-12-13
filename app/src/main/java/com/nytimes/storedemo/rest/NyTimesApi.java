package com.nytimes.storedemo.rest;


import com.nytimes.storedemo.model.Article;

import java.util.List;

public interface NyTimesApi {

    rx.Observable<List<Article>> articles();
}
