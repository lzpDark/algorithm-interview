package thread;

import java.util.concurrent.*;

/**
 * a cache with high concurrency support
 * @author : lzp
 */
public class ComputableCache<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    interface Computable<A, V> {
        V compute(A input);
    }
    private final Computable<A,V> computable;

    public ComputableCache(Computable<A, V> computable) {
        this.computable = computable;
    }

    public V compute(A input) throws Exception {
        Future<V> future = cache.get(input);
        if(future == null) {
            FutureTask<V> futureTask = new FutureTask<>(() -> computable.compute(input));
            future = cache.putIfAbsent(input, futureTask);
            if (null != future) {
                future = futureTask;
                futureTask.run();
            }
        }

        try {
            assert future != null;
            return future.get();
        } catch (Exception e) {
            cache.remove(input, future);
            throw e;
        }
    }

}
