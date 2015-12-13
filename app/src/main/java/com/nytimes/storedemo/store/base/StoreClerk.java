package com.nytimes.storedemo.store.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that manages all stores
 */
public class StoreClerk {

    private static final Set<BaseStore> storeSet = Collections.synchronizedSet(new HashSet<BaseStore>());

    /**
     * Called by construct of {@link BaseStore} to allow management by StoreClerk
     *
     * @param store
     */
    public static <Raw, Parsed> void register(BaseStore<Raw, Parsed> store) {
        storeSet.add(store);
    }

    /**
     * Clear memory for all stores
     */
    public static void clearMemory() {
        for (BaseStore store : storeSet) {
            store.clearMemory();
        }
    }
}
