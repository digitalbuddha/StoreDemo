package com.nytimes.storedemo.store.util;

/**
 * The purpose of this class (and subclasses) is to encapsulate all the information
 * that uniquely identifies an object. Think of it as a compound primary key in a relational
 * database, or a URL path in a REST GET request. Every Id is comprised of at least a type
 * (i.e. a class) and key (i.e. a string). For example:
 *
 *    object                               Id
 *   ------------------------------------------------------------------------------
 *   "fashion" section                     type=Section.class, key="fashion"
 *   "homepage" section                    type=Section.class, key="homepage"
 *   latest feeds                          type=LatestFeed.class, key="latestfeeds"
 *
 * The idea is that in the future, new Id subclasses might include further fields that
 * uniquely identify an object, e.g. the Id for an object representing a listing of articles
 * might include pagination info, sort order, etc.
 *
 * One advantage of encapsulating all this stuff in an Id object is that adding more fields
 * does not mean changing the signature of the methods that take the Id as input. It also makes
 * it easier to maintain the same representation of an object reference across different datastores,
 * caches, network APIs, etc.
 *
 */
public class Id<T> {


//    private static final Logger logger = LoggerFactory.getLogger(Id.class);

    protected final Class<T> type;
    protected final String key;

    protected final String descriptiveString; // for humans, e.g. in logs

    protected Id(Class<T> type, String key) {

        if (type == null) {
            throw new NullPointerException("need a type to create an Id");
        }

        if (key == null) {
            throw new NullPointerException("need a key to create an Id");
        }

        this.type = type;
        this.key = key;

        this.descriptiveString = generateDisplayString(type, key);
    }

    public static <S> Id<S> of(Class<S> type, String key) {
        // we might want to cache these in the future, thus the hidden constructor
        return new Id<>(type, key);
    }

    public Class<T> getType() {
        return type;
    }

    public String getKey() {
        return key;
    }


    protected static String generateDisplayString(Class type, String key) {

        if (key == null) {
            return String.format("any %s", type.getSimpleName());
        } else {
            return String.format("%s '%s'", type.getSimpleName(), key);
        }

    }


    @Override
    public String toString() {
        return descriptiveString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Id)) {
            return false;
        }

        Id id = (Id) o;

        if (!key.equals(id.key)) {
            return false;
        }
        if (!type.equals(id.type)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }


}
