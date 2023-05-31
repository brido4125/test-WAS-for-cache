package arcus.app.common.basic.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;


public class TpsTest {
    @Test
    void tps1000Test() throws Exception {

        int core = Runtime.getRuntime().availableProcessors();

        CountDownLatch latch = new CountDownLatch(1000);


        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                core,
                1000,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(500);
                    latch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("time second : " + (end - start) / 1000.0);
        System.out.println("executorService.getPoolSize() = " + executorService.getPoolSize());
    }

    @Test
    void changeMaxPoolThread() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                8,
                1000,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
        threadPool.setMaximumPoolSize(300);
        for (int i = 0; i < 300; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
