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

    private final long TPS = 1000;//사용자가 기대하는 WAS의 TPS 값
    private final double QUERY_TIME = 0.5;//해당 쿼리의 DB Latancy, 단위 second
    private final double QUERY_RATIO = 0.1;//TX내에서 해당 쿼리가 차지하는 비율

    @Test
    void tpsFormula() {
        double maxThreadPoolSize = QUERY_TIME * (TPS * QUERY_RATIO);
        int i = (int) maxThreadPoolSize;
        System.out.println("i = " + i);
    }
}
