# StoreDemo
Working with data when off or online is hard. This project aims to make it easier 

Retrofit/okhttp for networking
DiskLRU for persistence 
Guava for in memory caching 
RxJava & Dagger to bring it all home

Basic concept:
Using interceptors we are able to set cache control headers which ok http then uses to cache on disk.   When a new network request comes in while offline cached entry will be used for 4weeks,when online cached entry will be used for 1minute, otherwise network hit is made. 

Guava cache sits on top of this to cache inflated models in memory. Immutables are used both for immutability as well as to generate gson type adapters. Loads from network & disk have zero reflection. 

all data logic is abstracted into a reactive data store. UI doesn't need to worry about where data is coming from. Ui can bypass or clear memory Cache. 

Dagger is used in demo project but not required. 

