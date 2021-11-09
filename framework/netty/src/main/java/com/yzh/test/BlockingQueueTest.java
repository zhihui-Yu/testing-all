package com.yzh.test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author simple
 */
public class BlockingQueueTest {
    public static ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        arrayBlockingQueue.add(1);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                Integer poll = arrayBlockingQueue.poll();
                arrayBlockingQueue.add(poll + 1);
                System.out.println(Thread.currentThread().getName() + "-- poll number : " + poll);
                System.out.println(Thread.currentThread().getName() + "-- add number : " + (poll + 1));
            }).start();
        }
        System.out.println("queue size : " + arrayBlockingQueue.size());
    }
}
