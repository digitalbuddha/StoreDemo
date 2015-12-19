# StoreDemo
Working with data whether offline or while connected is hard to do in a seamless manner. This project aims to make it easier. 


Basic concept:
OkHTTP contains the ability to cache on disk, Caching is done by reading Cache-Control headers.  
```
OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(new Cache(cacheDir, 1024));
  ```

Using retrofit request interceptors we are able to set those cache control headers on a per request basis basis using something like:

```
            if (network.isOnGoodConnection()) {
                int maxAge = 60; // read from cache for 1 minute
                request.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request.addHeader("Cache-Control",
                        "public, only-if-cached, max-stale=" + maxStale);
            }
        ```

   When a new network request comes in while offline cached entry will be used for 4weeks, when network status comes back as good, online cached entry will be used for 1minute, otherwise network hit is made. We create a retrofit instance that uses the OkHttp client with cache.  We also add a request inteceptor with the above logic
   ```
   RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setRequestInterceptor(interceptor);
                .setConverter(new GsonConverter(gson))

  ```
  We also pass in a GsonConverter with a custom Gson instance that has type adapters generated for all data models.  Retrofit will use the same gson instance whether reading from disk or network. Save your self and startup a lot of time by relying on Immutables(Immutables.org) to generate your type adapters on a per package basis. 
  ```
  @Gson.TypeAdapters
package com.nytimes.storedemo.model;

import org.immutables.gson.Gson;
```
 Loads from network & disk have zero reflection and are blazing fast. Now that we have a Disk/Network layer, Why not put a Guava cache on top of this to cache inflated models in memory, the demo caches for 1 day. All of the above logic is abstracted into a reactive data store. UI doesn't need to worry about where data is coming from. UI can optionally bypass or clear memory cache. 


Libraries used:
Retrofit/okhttp for networking
DiskLRU for persistence 
Guava for in memory caching 
RxJava & Dagger to bring it all home

Dagger is used in demo project but not required. 

