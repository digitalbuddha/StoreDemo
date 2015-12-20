package com.digitalbuddha.daodemo.ui;

/**
 * Generic Presenter interface
 */
public interface Presenter<T> {

    void bind(T item);

    void unbind();
}