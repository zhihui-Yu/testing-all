package com.spi.test;

import com.spi.test.api.PaymentService;

import java.util.ServiceLoader;

/**
 * @author simple
 */
public class Main {
    public static void main(String[] args) {
        /**
         * spi: service provider interface
         * 可以用于项目解耦，如引入jdbc并不需要知道具体的driver实现类是什么，因为可以通过spi获取。
         */
        ServiceLoader<PaymentService> services = ServiceLoader.load(PaymentService.class);
        for (var service : services) {
            service.pay();
        }
    }
}
