package com.digitalbuddha.storedemo.ui;

/**
 * Generic Presenter interface
 */
public interface Presenter<T> {

    void bind(T item);

    void unbind();
}