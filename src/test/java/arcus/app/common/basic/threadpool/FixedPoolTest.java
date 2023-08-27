package arcus.app.common.basic.threadpool;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FixedPoolTest {
    @Test
    void fixedPoolTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0 ; i < 10 ; i++) {
            executorService.execute(() -> {
                sleep(20);
            });
        }
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executorService;
        int activeCount = threadPool.getActiveCount();
        System.out.println("activeCount = " + activeCount);
    }

    @Test
    void lessThanCoreNum() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0 ; i < 5 ; i++) {
            executorService.execute(() -> {
                sleep(20);
            });
        }
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executorService;
        sleep(1000);
        System.out.println("threadPool.getPoolsize() = " + threadPool.getPoolSize());
        Assertions.assertThat(threadPool.getPoolSize()).isLessThan(10);
    }


    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
