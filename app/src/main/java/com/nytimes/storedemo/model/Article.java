package com.nytimes.storedemo.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Gson.TypeAdapters
public abstract class Article  {
    public abstract String title();

    public abstract String body();

    public abstract List<Image> images();
}
