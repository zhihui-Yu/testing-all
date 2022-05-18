package com.spi.test.api.impl;

import com.spi.test.api.PaymentService;

/**
 * @author simple
 */
public class WXPaymentService implements PaymentService {
    @Override
    public void pay() {
        System.out.println("wx paid");
    }
}
