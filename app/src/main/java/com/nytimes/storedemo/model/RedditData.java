package com.nytimes.storedemo.model;

import org.immutables.value.Value;

@Value.Immutable
public abstract class RedditData {
    public abstract Kind kind();
}
