package arcus.app.common.basic.threadpool;

import com.jam2in.arcus.app.common.recaching.ArcusRecachingTask;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("Thread Pool 내부 Queue에 Task가 쌓이는 경우 - SynchronousQueue 성공 ")
    void threadPoolTaskQueueTest() {
        int numTasks = 60;
        SynchronousQueue<Runnable> queue = new SynchronousQueue<>();
        CountDownLatch latch = new CountDownLatch(numTasks);
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(10,
                        numTasks, 10L,
                        TimeUnit.SECONDS, queue);

        for (int i = 0; i < numTasks; i++) {
            threadPool.execute(() -> {
                sleep(1000);
                latch.countDown();
            });
        }

        for(int i = 0; i < 120; i++){
            sleep(500);
            //현재 실행 중인 Thread의 수 출력
            System.out.println("Active Thread: " + threadPool.getActiveCount());
            //현재 존재하는 Thread 수 출력
            System.out.println("Current Thread: " + threadPool.getPoolSize());
            //Queue에서 대기 중인 작업 갯수 출력
            System.out.println("Queue: " + queue.size());
            System.out.println();
        }

        threadPool.shutdown();
    }

    @Test
    @DisplayName("SynchronousQueue - Task가 쌓이는 경우")
    void synchronousQueueOfferTest() {
        SynchronousQueue<Runnable> queue = new SynchronousQueue<>();
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, 10L,
                        TimeUnit.SECONDS, queue);

        for (int i = 0; i < MAXIMUM_THREAD_POOL_SIZE; i++) {
            threadPool.execute(() -> {
                sleep(100000);
            });
        }

        assertThatThrownBy(() -> {
            threadPool.execute(() -> {
                sleep(10000);
            });
        }).isInstanceOf(RejectedExecutionException.class);
    }

    @Test
    @DisplayName("Thread Pool 내부 Queue에 Task가 쌓이는 경우 -  LinkedBlockingQueue ")
    void linkedBlockingQueueTest() {
        int numTasks = 60;
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        CountDownLatch latch = new CountDownLatch(numTasks);
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(10,
                        numTasks, 10L,
                        TimeUnit.SECONDS, queue);

        for (int i = 0; i < numTasks; i++) {
            threadPool.execute(() -> {
                sleep(1000);
                latch.countDown();
            });
        }

        for(int i = 0; i < 120; i++){
            sleep(500);
            //현재 실행 중인 Thread의 수 출력
            System.out.println("Active Thread: " + threadPool.getActiveCount());
            //현재 존재하는 Thread 수 출력
            System.out.println("Current Thread: " + threadPool.getPoolSize());
            //Queue에서 대기 중인 작업 갯수 출력
            System.out.println("Queue: " + queue.size());
            System.out.println();
        }



        threadPool.shutdown();
    }

    @Test
    @DisplayName("Task가 무한으로 수행되는 경우, 주어진 time이 지나도 자동 종료되지 않는다.")
    void testInfiniteTaskTest() {
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE,
                        MAXIMUM_THREAD_POOL_SIZE, 1L,
                        TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        for (int i = 0; i < CORE_THREAD_POOL_SIZE; i++) {
            threadPool.execute(() -> {
                while (!Thread.interrupted()) {
                    System.out.println("Thread Name :  ing" + Thread.currentThread().getName());
                    sleep(5000);
                }
            });
        }

        threadPool.execute(() -> {
            while (!Thread.interrupted()) {
                System.out.println("Thread Name :  ing" + Thread.currentThread().getName());
                sleep(500);
                if (threadPool.getPoolSize() > CORE_THREAD_POOL_SIZE) {
                    break;
                }
            }
            System.out.println("End Thread Name :  end " + Thread.currentThread().getName());
        });

        sleep(5000);

        assertThat(threadPool.getPoolSize()).isEqualTo(CORE_THREAD_POOL_SIZE);
        assertThat(threadPool.getActiveCount()).isEqualTo(CORE_THREAD_POOL_SIZE);
    }


    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
