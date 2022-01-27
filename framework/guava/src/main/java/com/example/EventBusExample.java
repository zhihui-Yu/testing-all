package com.example;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author simple
 */
public class EventBusExample {
    public static final String key = "KEY";

    public static void main(String[] args) {
//        HashMultimap<Object, String> map = HashMultimap.create();
//        map.put("1", "1");
//        map.put("1", "2");
//        System.out.println(map);

        EventBus eventBus = new EventBus(key);
        eventBus.register(new SubscribeOne());
        eventBus.register(new SubscribeTwo());
        System.out.println("同一个线程内时");
        eventBus.post(new EventOne());
        eventBus.post(new EventTwo());

//        System.out.println("不同一个线程内时");
//        Thread t = new Thread(() -> eventBus.post(new EventOne("1")));
//        Thread t2 = new Thread(() -> eventBus.post(new EventOne("2")));
//        t.start();
//        t2.start();
    }

    public static class SubscribeOne {
        @Subscribe
        public void handle(EventOne event) throws Exception {
//            throw new Exception("zzz");
            int sleep = 9000;
            if (event.msg.equals("1")){
                sleep = sleep - 3000;
            }
            Thread.sleep(sleep);
            System.out.println("one: " + event.msg + "," + event.value);
            System.out.println(Thread.currentThread().getName() + ": sleep = " + sleep + " " + event.msg + "," + event.value);
        }

        @Subscribe
        public void handle(EventTwo event) throws Exception {
//            throw new Exception("zzz");
            int sleep = ThreadLocalRandom.current().nextInt(10) * 1000;
            Thread.sleep(sleep);
            System.out.println("one: " + event.msg + "," + event.value);
            System.out.println(Thread.currentThread().getName() + ": sleep = " + sleep + " " + event.msg + "," + event.value);
        }

        public void handle2(EventOne event) {
            System.out.println("one: " + event.msg + "," + event.value);
        }
    }

    public static class SubscribeTwo {
        @Subscribe
        public void handle(EventTwo event) throws Exception {
            int sleep = ThreadLocalRandom.current().nextInt(10) * 1000;
            Thread.sleep(sleep);
            System.out.println("two: " + event.msg + "," + event.value);
            System.out.println(Thread.currentThread().getName() + ": sleep = " + sleep + " " + event.msg + "," + event.value);
        }

        public void handle2(EventTwo event) {
            System.out.println("two: " + event.msg + "," + event.value);
        }
    }

    public static abstract class Event {
        public String msg;

        public Event() {
            this("Hello World!");
        }

        public Event(String msg) {
            this.msg = msg;
        }
    }

    public static class EventOne extends Event {
        public final String value = "one-msg";

        public EventOne() {
            super("one");
        }

        public EventOne(String msg) {
            super(msg);
        }
    }

    public static class EventTwo extends Event {
        public final String value = "two-msg";

        public EventTwo() {
            super("two");
        }
    }
}
