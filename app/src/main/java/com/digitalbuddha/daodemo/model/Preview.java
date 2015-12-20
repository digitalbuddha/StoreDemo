package com.digitalbuddha.daodemo.model;

import org.immutables.value.Value;

import java.util.List;

/**
 * Created by brianplummer on 12/19/15.
 */
@Value.Immutable
public abstract class Preview {
    public abstract List<Images> images();
}
