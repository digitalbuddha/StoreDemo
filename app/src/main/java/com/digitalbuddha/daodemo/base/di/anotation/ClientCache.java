package com.digitalbuddha.daodemo.base.di.anotation;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by brianplummer on 12/19/15.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ClientCache {
}
