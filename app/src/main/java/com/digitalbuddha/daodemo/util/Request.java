package com.digitalbuddha.daodemo.util;

import rx.Observable;

/**
 * The purpose of this class (and subclasses) is to encapsulate all the information
 * that uniquely identifies an object. Think of it as a compound primary key in a relational
 * database, or a URL path in a REST GET request. Every Request is comprised of at least a type
 * (i.e. a class) and key (i.e. a string). For example:
 *
 *    object                               Request
 *   ------------------------------------------------------------------------------
 *   "fashion" section                     type=Section.class, key="fashion"
 *   "homepage" section                    type=Section.class, key="homepage"
 *   latest feeds                          type=LatestFeed.class, key="latestfeeds"
 *
 * The idea is that in the future, new Request subclasses might include further fields that
 * uniquely identify an object, e.g. the Request for an object representing a listing of articles
 * might include pagination info, sort order, etc.
 *
 * One advantage of encapsulating all this stuff in an Request object is that adding more fields
 * does not mean changing the signature of the methods that take the Request as input. It also makes
 * it easier to maintain the same representation of an object reference across different datastores,
 * caches, network APIs, etc.
 *
 */

    public class Request<T> extends Identifier<Class<T>, String> {

        protected Request(Class<T> type, String key, Observable<T> response) {
            super(type, key,response);
        }

        public static <S> Request<S> of(Class<S> type, String key, Observable<S> response) {
            // we might want to cache these in the future, thus the hidden constructor
            return new Request<>(type, key,response);
        }

    }
