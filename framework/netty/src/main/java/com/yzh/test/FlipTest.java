package com.yzh.test;

import java.nio.IntBuffer;

/**
 * @author simple
 */
public class FlipTest {
    // doc link https://blog.csdn.net/u013096088/article/details/78638245
    public static void main(String[] args) {
        // 分配内存大小为10的缓存区
        IntBuffer buffer = IntBuffer.allocate(10);

        System.out.println("capacity:" + buffer.capacity());

        for (int i = 0; i < 5; ++i) {
//            int randomNumber = new SecureRandom().nextInt(20);
//            buffer.put(randomNumber);
            buffer.put(i);
        }

        System.out.println("============================");

        System.out.println("before flip limit:" + buffer.limit());
        buffer.flip();

        System.out.println("after flip limit:" + buffer.limit());

        System.out.println("============================");
        System.out.println("enter while loop");

        while (buffer.hasRemaining()) {
            System.out.println("元素:" + buffer.get());
            System.out.println("position:" + buffer.position());
            System.out.println("limit:" + buffer.limit());
            System.out.println("capacity:" + buffer.capacity());
            System.out.println();
        }
    }
}
