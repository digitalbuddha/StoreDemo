package com.digitalbuddha.daodemo.reddit.data.model;

import com.google.common.base.Optional;

import org.immutables.value.Value;

/**
 * Created by brianplummer on 12/19/15.
 */
@Value.Immutable
public abstract class PostData {
    public abstract String title();
    public abstract String thumbnail();
    public abstract Optional<Preview> preview();
}
