package com.yzh.test;

/**
 * @author simple
 */
public class ThreadLocalTest {
    public static volatile int temp = 0;
    //线程本地存储变量
    private static final ThreadLocal<Integer> THREAD_LOCAL_NUM = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {//启动三个线程
            Thread t = new Thread() {
                @Override
                public void run() {
                    add10ByThreadLocal();
                    add10ByVolatile();
                }
            };
            t.start();
        }
    }

    private static void add10ByVolatile() {
        temp +=1;
        System.out.println(Thread.currentThread().getName() + " : volatile temp =" + temp);
    }
    /**
     * 线程本地存储变量加 5
     */
    private static void add10ByThreadLocal() {
        for (int i = 0; i < 2; i++) {
            Integer n = THREAD_LOCAL_NUM.get();
            n += 1;
            THREAD_LOCAL_NUM.set(n);
            System.out.println(Thread.currentThread().getName() + " : ThreadLocal num=" + n);
        }
    }

}