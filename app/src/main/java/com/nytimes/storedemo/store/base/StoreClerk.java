package com.nytimes.storedemo.store.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that manages all stores
 */
public class StoreClerk {

    private static final Set<RxStore> storeSet = Collections.synchronizedSet(new HashSet<RxStore>());

    /**
     * Called by construct of {@link Store} to allow management by StoreClerk
     *
     * @param store
     */
    public static <Raw, Parsed> void register(RxStore<Parsed> store) {
        storeSet.add(store);
    }

    /**
     * Clear get for all stores
     */
    public static void clearMemory() {
        for (Store store : storeSet) {
            store.clearMemory();
        }
    }
}
