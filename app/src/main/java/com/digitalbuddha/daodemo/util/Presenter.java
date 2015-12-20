package com.digitalbuddha.daodemo.util;

/**
 * Generic Presenter interface
 */
public interface Presenter<T> {

    void bind(T item);

    void unbind();
}