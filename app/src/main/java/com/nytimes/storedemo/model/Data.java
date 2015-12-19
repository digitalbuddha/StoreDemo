package com.nytimes.storedemo.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public abstract class Data {
    public abstract Children children();
}
