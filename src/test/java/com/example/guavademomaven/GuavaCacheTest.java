package com.example.guavademomaven;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.graph.Graph;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.junit.jupiter.api.Test;

import javax.sound.midi.Soundbank;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/7 12:08
 */
public class GuavaCacheTest {

    @Test
    public void test()  {
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(System.out::println)
                .build(
                        new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) throws Exception {
                                //return createExpensiveGraph(key);
                                System.err.println("create value");
                                return "Hello 1" + key;
                            }
                        });

        IntStream.rangeClosed(0, 3).forEach(__->{
            String s = null;
            try {
                s = graphs.get("123");
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(s);
        });


        graphs.cleanUp();
        System.out.println(graphs);
    }

    /**
     * Guava provides three basic types of eviction:
     * 1. size-based eviction,
     * 2. time-based eviction,
     * 3. reference-based eviction.
     */
    public void testEviction(){
        // Explicit Removals
        // At any time, you may explicitly invalidate cache entries rather than waiting for entries to be
        // evicted. This can be done:
        // individually, using Cache.invalidate(key)
        // in bulk, using Cache.invalidateAll(keys)
        // to all entries, using Cache.invalidateAll()

        //CacheLoader<Key, DatabaseConnection> loader = new CacheLoader<Key, DatabaseConnection> () {
        //    public DatabaseConnection load(Key key) throws Exception {
        //        return openConnection(key);
        //    }
        //};
        //RemovalListener<Key, DatabaseConnection> removalListener = new RemovalListener<Key, DatabaseConnection>() {
        //    public void onRemoval(RemovalNotification<Key, DatabaseConnection> removal) {
        //        DatabaseConnection conn = removal.getValue();
        //        conn.close(); // tear down properly
        //    }
        //};
        //
        //return CacheBuilder.newBuilder()
        //        .expireAfterWrite(2, TimeUnit.MINUTES)
        //        .removalListener(removalListener)
        //        .build(loader);
    }

    public void refreshCache(){
        // A CacheLoader may specify smart behavior to use on a refresh by overriding
        // CacheLoader.reload(K, V), which allows you to use the old value in computing the new value.

        //// Some keys don't need refreshing, and we want refreshes to be done asynchronously.
        //LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
        //        .maximumSize(1000)
        //        .refreshAfterWrite(1, TimeUnit.MINUTES)
        //        .build(
        //                new CacheLoader<Key, Graph>() {
        //                    public Graph load(Key key) { // no checked exception
        //                        return getGraphFromDatabase(key);
        //                    }
        //
        //                    public ListenableFuture<Graph> reload(final Key key, Graph prevGraph) {
        //                        if (neverNeedsRefresh(key)) {
        //                            return Futures.immediateFuture(prevGraph);
        //                        } else {
        //                            // asynchronous!
        //                            ListenableFutureTask<Graph> task = ListenableFutureTask.create(new Callable<Graph>() {
        //                                public Graph call() {
        //                                    return getGraphFromDatabase(key);
        //                                }
        //                            });
        //                            executor.execute(task);
        //                            return task;
        //                        }
        //                    }
        //                });
    }

    public void others(){
//        Cache<Key, Value> cache = CacheBuilder.newBuilder()
//                .maximumSize(1000)
//                .build(); // look Ma, no CacheLoader
//...
//        try {
//            // If the key wasn't in the "easy to compute" group, we need to
//            // do things the hard way.
//            cache.get(key, new Callable<Value>() {
//                @Override
//                public Value call() throws AnyException {
//                    return doThingsTheHardWay(key);
//                }
//            });
//        } catch (ExecutionException e) {
//            throw new OtherException(e.getCause());
//        }
    }
}
