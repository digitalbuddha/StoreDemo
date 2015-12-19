package com.nytimes.storedemo.model;

import com.google.common.base.Optional;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public abstract class Image {
    public abstract String url();
    public abstract Optional<Boolean> isTopImage();
}
