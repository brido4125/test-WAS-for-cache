package arcus.app.common.basic.threadfactory;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

    private long threadCounter = 1L;

    public MyThreadFactory(long threadNumber, long threadCounter) {
        this.threadCounter = threadCounter;
    }

    /*
    * Runnable을 인자로 받아서 해당 Runnable을 실행하는 Thread를 생성하는 메소드
    * */
    @Override
    public Thread newThread(Runnable r) {
        return null;
    }


    static public class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread Name : " + Thread.currentThread().getName());
            sleep(10000);
        }


        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

