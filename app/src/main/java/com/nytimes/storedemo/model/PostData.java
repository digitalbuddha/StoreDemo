package com.nytimes.storedemo.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * Created by brianplummer on 12/19/15.
 */
@Value.Immutable
public abstract class PostData {
    public abstract String title();
    public abstract String thumbnail();
}
