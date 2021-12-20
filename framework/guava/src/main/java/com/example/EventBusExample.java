package com.example;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author simple
 */
public class EventBusExample {
    public static final String key = "KEY";

    public static void main(String[] args) {
        EventBus eventBus = new EventBus(key);
        eventBus.register(new SubscribeOne());
        eventBus.register(new SubscribeTwo());
        eventBus.post(new EventOne());
        eventBus.post(new EventTwo());
    }

    public static class SubscribeOne {
        @Subscribe
        public void handle(EventOne event) {
            System.out.println("one: " + event.msg + "," + event.value);
        }

        public void handle2(EventOne event) {
            System.out.println("one: " + event.msg + "," + event.value);
        }
    }

    public static class SubscribeTwo {
        @Subscribe
        public void handle(EventTwo event) {
            System.out.println("two: " + event.msg + "," + event.value);
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
    }

    public static class EventTwo extends Event {
        public final String value = "two-msg";

        public EventTwo() {
            super("two");
        }
    }
}
