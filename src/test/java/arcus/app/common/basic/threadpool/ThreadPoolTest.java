package arcus.app.common.basic.threadpool;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadPoolTest {

    private static final int CORE_THREAD_POOL_SIZE = 3;

    private static final int MAXIMUM_THREAD_POOL_SIZE = 5;
    private static final long KEEP_ALIVE_TIME = 5L;

    @Test
    @DisplayName("CORE THREAD POOL SIZE == 처리할 Task의 수")
    void cachedNewThreadPoolTest() {
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < CORE_THREAD_POOL_SIZE; i++) {
            threadPool.execute(() -> {
                System.out.println("Thread Name : " + Thread.currentThread().getName());
                sleep(10000);
            });
        }
        assertThat(threadPool.getPoolSize()).isEqualTo(CORE_THREAD_POOL_SIZE);
        assertThat(threadPool.getActiveCount()).isEqualTo(CORE_THREAD_POOL_SIZE);

    }

    @Test
    @DisplayName("MAXIMUM_THREAD_POOL_SIZE == Task의 수")
    void setMaximumThreadPoolSizeTest() {
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < MAXIMUM_THREAD_POOL_SIZE; i++) {
            threadPool.execute(() -> {
                System.out.println("Thread Name : " + Thread.currentThread().getName());
                sleep(10000);
            });
        }
        assertThat(threadPool.getPoolSize()).isEqualTo(MAXIMUM_THREAD_POOL_SIZE);
        assertThat(threadPool.getActiveCount()).isEqualTo(MAXIMUM_THREAD_POOL_SIZE);
    }

    @Test
    @DisplayName("CORE THREAD POOL SIZE < Task의 수 < MAXIMUM_THREAD_POOL_SIZE")
    void taskCountBetweenCoreAndMax() {

        int taskCount = 4;

        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < taskCount; i++) {
            threadPool.execute(() -> {
                System.out.println("Thread Name : " + Thread.currentThread().getName());
                sleep(10000);
            });
        }
        assertThat(threadPool.getPoolSize()).isEqualTo(taskCount);
        assertThat(threadPool.getActiveCount()).isEqualTo(taskCount);
    }

    @Test
    @DisplayName("CORE THREAD POOL SIZE < Task의 수 < MAXIMUM_THREAD_POOL_SIZE , 각 Task의 소요 시간이 다른 경우")
    void taskCountBetweenCoreAndMax2() {

        int taskCount = 4;

        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < CORE_THREAD_POOL_SIZE; i++) {
            threadPool.execute(() -> {
                System.out.println("Thread Name : " + Thread.currentThread().getName());
                sleep(10000);
            });
        }

        assertThat(threadPool.getPoolSize()).isEqualTo(CORE_THREAD_POOL_SIZE);
        assertThat(threadPool.getActiveCount()).isEqualTo(threadPool.getCorePoolSize());

        sleep(1000);//1초 후에 다른 Task 추가

        threadPool.execute(() -> {
            System.out.println("Thread Name : " + Thread.currentThread().getName());
            sleep(10000);
        });

        assertThat(threadPool.getPoolSize()).isEqualTo(CORE_THREAD_POOL_SIZE + 1);
        assertThat(threadPool.getActiveCount()).isEqualTo(CORE_THREAD_POOL_SIZE + 1);
    }



    @Test
    @DisplayName("ExecutorService를 casting 하여 ThreadPoolExecutor의 메소드를 사용할 수 있다.")
    void executorServiceCastingTest() {
        ExecutorService executorService =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                System.out.println("Thread Name : " + Thread.currentThread().getName());
                sleep(10000);
            });
        }

        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executorService;

        assertThat(threadPool.getPoolSize()).isEqualTo(3);
        assertThat(threadPool.getActiveCount()).isEqualTo(3);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
